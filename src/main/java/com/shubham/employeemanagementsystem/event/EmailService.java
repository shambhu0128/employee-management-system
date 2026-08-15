package com.shubham.employeemanagementsystem.service;

import com.shubham.employeemanagementsystem.event.EmployeeCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendWelcomeEmail(EmployeeCreatedEvent event) {
        // Stub for now — swap for real SMTP/SES/SendGrid integration later.
        logger.info("Simulated email sent to {} (employee ID: {})", event.getEmail(), event.getEmployeeId());
    }
}