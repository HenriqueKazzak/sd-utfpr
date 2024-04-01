package com.kazzak.srvproducera.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnCallNotPermittedEvent
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnErrorEvent
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent
import io.github.resilience4j.core.EventConsumer
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration

@Configuration
class CBConfig @Autowired constructor(private val circuitBreakerRegistry: CircuitBreakerRegistry,
                                      private val rabbitTemplate: RabbitTemplate
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    private val objectMapper = ObjectMapper();

    init {
        val eventConsumerTransition = EventConsumer<CircuitBreakerOnStateTransitionEvent> { event ->
            log.info("Circuit breaker ${event.circuitBreakerName} state changed from ${event.stateTransition.fromState} to ${event.stateTransition.toState}")
            if (event.stateTransition.toState == CircuitBreaker.State.OPEN) {
                rabbitTemplate.convertAndSend("cb-events-exchange", "open", objectMapper.writeValueAsBytes(circuitBreakerRegistry.circuitBreaker(event.circuitBreakerName).metrics))
            }
        }
        val eventConsumerOnError = EventConsumer<CircuitBreakerOnErrorEvent> { event ->
            log.error("Circuit breaker error: ${event.throwable.message}")
        }
        val eventConsumerOpen = EventConsumer<CircuitBreakerOnCallNotPermittedEvent> { event ->
            log.error("Circuit breaker ${event.circuitBreakerName} is open")
        }
        circuitBreakerRegistry.circuitBreaker("externalService").eventPublisher.onStateTransition(eventConsumerTransition)
        circuitBreakerRegistry.circuitBreaker("externalService").eventPublisher.onError(eventConsumerOnError)
        circuitBreakerRegistry.circuitBreaker("externalService").eventPublisher.onCallNotPermitted(eventConsumerOpen)
    }

}