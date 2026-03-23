package com.ashish.jobtracker.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Queue name — where messages will sit until consumed
    public static final String STATUS_QUEUE = "application.status.changed";

    // Exchange name — receives messages from producer and routes to queues
    public static final String STATUS_EXCHANGE = "application.exchange";

    // Routing key — the address exchange uses to find the right queue
    public static final String STATUS_ROUTING_KEY = "application.status";

    @Bean
    public Queue statusQueue() {
        // durable = true means queue survives RabbitMQ restart
        return new Queue(STATUS_QUEUE, true);
    }

    @Bean
    public DirectExchange statusExchange() {
        return new DirectExchange(STATUS_EXCHANGE);
    }

    @Bean
    public Binding statusBinding(Queue statusQueue, DirectExchange statusExchange) {
        return BindingBuilder
                .bind(statusQueue)
                .to(statusExchange)
                .with(STATUS_ROUTING_KEY);
    }

    // Converts Java objects to JSON automatically when sending/receiving messages
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}