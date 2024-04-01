package com.kazzak.srvproducera.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.reflect.jvm.internal.impl.utils.CollectionsKt

@Configuration
class RabbitConfig {

    val QUEUE_NAME = "cb-open-queue"
    val EXCHANGE_NAME = "cb-events-exchange"

    @Bean
    fun queue(): Queue {
        return Queue(QUEUE_NAME, true, false, false, mapOf("x-queue-type" to "quorum"))
    }

    @Bean
    fun topicExchange(): TopicExchange {
        return TopicExchange(EXCHANGE_NAME)
    }

    @Bean
    fun binding(): Binding {
        return BindingBuilder.bind(queue()).to(topicExchange()).with("open")
    }


}