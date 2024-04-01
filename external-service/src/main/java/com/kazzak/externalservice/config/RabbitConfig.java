package com.kazzak.externalservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RabbitConfig {

    private String QUEUE_NAME = "onboarding-queue";
    private String EXCHANGE_NAME = "client-data-exchange";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true, false, false, Map.of("x-queue-type", "quorum"));
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding()  {
        return BindingBuilder.bind(queue()).to(topicExchange()).with("new");
    }
}
