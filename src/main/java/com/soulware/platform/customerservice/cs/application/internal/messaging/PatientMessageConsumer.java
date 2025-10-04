package com.soulware.platform.customerservice.cs.application.internal.messaging;

import com.soulware.platform.customerservice.cs.application.internal.services.CompleteExcelProcessor;
import com.soulware.platform.customerservice.cs.interfaces.rest.dto.ExcelProcessingResult;
import com.soulware.platform.customerservice.cs.interfaces.rest.dto.PatientProcessingMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class PatientMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PatientMessageConsumer.class);
    
    private final CompleteExcelProcessor excelProcessor;

    public PatientMessageConsumer(CompleteExcelProcessor excelProcessor) {
        this.excelProcessor = excelProcessor;
    }

    @JmsListener(destination = "${app.queue.patient-processing}")
    public void processPatientMessage(PatientProcessingMessage message) {
        logger.info("Processing patient message: {}", message.messageId());
        
        try {
            ExcelProcessingResult result = excelProcessor.processCompleteData(message.patientData());
            
            if (result.success()) {
                logger.info("Successfully processed patient message: {}", message.messageId());
            } else {
                logger.error("Failed to process patient message: {} - {}", 
                    message.messageId(), result.message());
            }
        } catch (Exception e) {
            logger.error("Error processing patient message: {}", message.messageId(), e);
        }
    }
}