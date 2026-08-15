package com.shubham.employeemanagementsystem.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmployeeEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeEventProducer.class);
    private static final String TOPIC = "employee-created-topic";

    @Autowired
    private KafkaTemplate<String, EmployeeCreatedEvent> kafkaTemplate;

    public void publishEmployeeCreated(EmployeeCreatedEvent event) {
        logger.info("Publishing EmployeeCreatedEvent for employee ID: {}", event.getEmployeeId());
        kafkaTemplate.send(TOPIC, event.getEmployeeId().toString(), event);
    }
}