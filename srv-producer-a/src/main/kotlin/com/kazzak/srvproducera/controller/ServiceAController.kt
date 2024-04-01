package com.kazzak.srvproducera.controller

import com.kazzak.srvproducera.model.ClientData
import com.kazzak.srvproducera.service.ServiceWebClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class ServiceAController(private val serviceWebClient: ServiceWebClient) {

    @PostMapping("/api/v1/service-a")
    fun serviceA(@RequestBody request: ClientData): Mono<String> {
        return serviceWebClient.post(request)
    }
}