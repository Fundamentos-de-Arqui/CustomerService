package com.soulware.platform.customerservice.cs.application.internal.messaging;

import com.soulware.platform.customerservice.cs.interfaces.rest.dto.CompletePatientDataRequest;
import com.soulware.platform.customerservice.cs.interfaces.rest.dto.PatientProcessingMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PatientMessageProducer {

    private final JmsTemplate jmsTemplate;

    @Value("${app.queue.patient-processing}")
    private String queueName;

    public PatientMessageProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public String sendPatientForProcessing(CompletePatientDataRequest patientData, String fileName) {
        String messageId = UUID.randomUUID().toString();
        
        PatientProcessingMessage message = new PatientProcessingMessage(
            messageId,
            fileName,
            LocalDateTime.now(),
            patientData,
            0,
            "PENDING"
        );

        try {
            jmsTemplate.convertAndSend(queueName, message);
            System.out.println("Successfully sent message to queue: " + queueName + " with ID: " + messageId);
            return messageId;
        } catch (Exception e) {
            System.err.println("Failed to send message to queue: " + e.getMessage());
            throw new RuntimeException("Failed to send message to JMS queue", e);
        }
    }
}