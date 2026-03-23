package com.ashish.jobtracker.messaging;

import com.ashish.jobtracker.config.RabbitMQConfig;
import com.ashish.jobtracker.model.StatusChangedMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatusChangeProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendStatusChange(StatusChangedMessage message) {
        log.info("Publishing status change event for application: {} -> new status: {}",
                message.getApplicationId(), message.getNewStatus());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.STATUS_EXCHANGE,
                RabbitMQConfig.STATUS_ROUTING_KEY,
                message
        );

        log.info("Message published successfully to exchange: {}", RabbitMQConfig.STATUS_EXCHANGE);
    }
}