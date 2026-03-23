package com.ashish.jobtracker.messaging;

import com.ashish.jobtracker.config.RabbitMQConfig;
import com.ashish.jobtracker.model.StatusChangedMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StatusChangeConsumer {

    @RabbitListener(queues = RabbitMQConfig.STATUS_QUEUE)
    public void handleStatusChange(StatusChangedMessage message) {
        log.info("Received status change event for application ID: {}", message.getApplicationId());
        log.info("Applicant: {} ({})", message.getApplicantName(), message.getApplicantEmail());
        log.info("Company: {} | Position: {}", message.getCompanyName(), message.getPosition());
        log.info("Status changed: {} → {}", message.getOldStatus(), message.getNewStatus());

        // This is where you would send an actual email
        // For now we simulate it with a log
        sendEmailNotification(message);
    }

    private void sendEmailNotification(StatusChangedMessage message) {
        // Simulated email — replace with JavaMailSender later
        log.info("---------------------------------------------");
        log.info("📧 Sending email to: {}", message.getApplicantEmail());
        log.info("Subject: Your application status has been updated");
        log.info("Body: Hi {}, your application for {} at {} has been updated to: {}",
                message.getApplicantName(),
                message.getPosition(),
                message.getCompanyName(),
                message.getNewStatus());
        log.info("---------------------------------------------");
    }
}