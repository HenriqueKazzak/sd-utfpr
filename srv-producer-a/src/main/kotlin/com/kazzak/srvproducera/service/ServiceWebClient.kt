package com.kazzak.srvproducera.service

import com.kazzak.srvproducera.model.ClientData
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserter
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class ServiceWebClient(private val webClient: WebClient) {

    private val log = LoggerFactory.getLogger(this.javaClass)


    @CircuitBreaker(name = "externalService", fallbackMethod = "fallback")
    fun post(request:ClientData): Mono<String> {
       return webClient.post()
            .uri("/api/v1/cliente")
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .onStatus({ status -> status.is4xxClientError || status.is5xxServerError }) { response -> response.createException() }
            .bodyToMono(String::class.java)
            .onErrorResume { throwable -> Mono.error(throwable)}
    }

    fun fallback(clientData: ClientData,throwable: Throwable): String {
        log.error("Fallback called with exception: $throwable")
        return "Fallback response"
    }
}