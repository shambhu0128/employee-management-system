package com.shubham.employeemanagementsystem.event;

import com.shubham.employeemanagementsystem.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmployeeEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeEventConsumer.class);

    private final EmailService emailService;

    public EmployeeEventConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "employee-created-topic", groupId = "ems-group")
    public void consume(EmployeeCreatedEvent event) {
        logger.info("Received EmployeeCreatedEvent for employee ID: {}", event.getEmployeeId());
        emailService.sendWelcomeEmail(event);
    }
}