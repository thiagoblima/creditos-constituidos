package com.lima.software.creditos_constituidos.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class KafkaProducerServiceTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducerService kafkaProducerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendConsultaEvent_shouldSendMessageToKafka() {
        // Arrange
        String message = "Consulta por NFS-e: 12345";
        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(anyString(), anyString())).thenReturn(future);

        // Act
        kafkaProducerService.sendConsultaEvent(message);

        // Assert
        verify(kafkaTemplate, times(1)).send(eq("creditos-consultas"), eq(message));
    }

    @Test
    void constructor_shouldInitializeKafkaTemplate() {
        // Arrange
        KafkaTemplate<String, String> template = mock(KafkaTemplate.class);
        
        // Act
        KafkaProducerService service = new KafkaProducerService(template);
        
        // Assert - Verify the service was created with the template
        // We'll use the sendConsultaEvent method to indirectly verify the template was set
        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(template.send(anyString(), anyString())).thenReturn(future);
        
        service.sendConsultaEvent("test");
        verify(template, times(1)).send(eq("creditos-consultas"), eq("test"));
    }
}
