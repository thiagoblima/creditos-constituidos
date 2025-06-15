package com.lima.software.creditos_constituidos.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApiErrorTest {

    @Test
    void testNoArgsConstructor() {
        // Act
        ApiError apiError = new ApiError();
        
        // Assert
        assertNotNull(apiError);
        assertNull(apiError.getStatus());
        assertNull(apiError.getTimestamp());
        assertNull(apiError.getMessage());
        assertNull(apiError.getDebugMessage());
        assertNull(apiError.getSubErrors());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        HttpStatus status = HttpStatus.BAD_REQUEST;
        LocalDateTime timestamp = LocalDateTime.now();
        String message = "Erro de validação";
        String debugMessage = "Campo obrigatório não informado";
        List<ApiSubError> subErrors = new ArrayList<>();
        subErrors.add(new ApiValidationError("objeto", "campo", "valor", "mensagem"));
        
        // Act
        ApiError apiError = new ApiError(status, timestamp, message, debugMessage, subErrors);
        
        // Assert
        assertEquals(status, apiError.getStatus());
        assertEquals(timestamp, apiError.getTimestamp());
        assertEquals(message, apiError.getMessage());
        assertEquals(debugMessage, apiError.getDebugMessage());
        assertEquals(subErrors, apiError.getSubErrors());
    }

    @Test
    void testStatusOnlyConstructor() {
        // Arrange
        HttpStatus status = HttpStatus.NOT_FOUND;
        
        // Act
        ApiError apiError = new ApiError(status);
        
        // Assert
        assertEquals(status, apiError.getStatus());
        assertNotNull(apiError.getTimestamp());
        assertNull(apiError.getMessage());
        assertNull(apiError.getDebugMessage());
        assertNull(apiError.getSubErrors());
    }

    @Test
    void testStatusAndMessageConstructor() {
        // Arrange
        HttpStatus status = HttpStatus.FORBIDDEN;
        String message = "Acesso negado";
        
        // Act
        ApiError apiError = new ApiError(status, message);
        
        // Assert
        assertEquals(status, apiError.getStatus());
        assertNotNull(apiError.getTimestamp());
        assertEquals(message, apiError.getMessage());
        assertNull(apiError.getDebugMessage());
        assertNull(apiError.getSubErrors());
    }

    @Test
    void testStatusMessageAndThrowableConstructor() {
        // Arrange
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Erro interno do servidor";
        Exception exception = new RuntimeException("Detalhes do erro");
        
        // Act
        ApiError apiError = new ApiError(status, message, exception);
        
        // Assert
        assertEquals(status, apiError.getStatus());
        assertNotNull(apiError.getTimestamp());
        assertEquals(message, apiError.getMessage());
        assertEquals(exception.getLocalizedMessage(), apiError.getDebugMessage());
        assertNull(apiError.getSubErrors());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        ApiError apiError = new ApiError();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        LocalDateTime timestamp = LocalDateTime.now();
        String message = "Erro de validação";
        String debugMessage = "Campo obrigatório não informado";
        List<ApiSubError> subErrors = new ArrayList<>();
        subErrors.add(new ApiValidationError("objeto", "campo", "valor", "mensagem"));
        
        // Act
        apiError.setStatus(status);
        apiError.setTimestamp(timestamp);
        apiError.setMessage(message);
        apiError.setDebugMessage(debugMessage);
        apiError.setSubErrors(subErrors);
        
        // Assert
        assertEquals(status, apiError.getStatus());
        assertEquals(timestamp, apiError.getTimestamp());
        assertEquals(message, apiError.getMessage());
        assertEquals(debugMessage, apiError.getDebugMessage());
        assertEquals(subErrors, apiError.getSubErrors());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        HttpStatus status = HttpStatus.BAD_REQUEST;
        LocalDateTime timestamp = LocalDateTime.now();
        String message = "Erro de validação";
        String debugMessage = "Campo obrigatório não informado";
        List<ApiSubError> subErrors = new ArrayList<>();
        subErrors.add(new ApiValidationError("objeto", "campo", "valor", "mensagem"));
        
        ApiError apiError1 = new ApiError(status, timestamp, message, debugMessage, subErrors);
        ApiError apiError2 = new ApiError(status, timestamp, message, debugMessage, subErrors);
        ApiError apiError3 = new ApiError(HttpStatus.NOT_FOUND, timestamp, "Outro erro", debugMessage, subErrors);
        
        // Act & Assert
        assertEquals(apiError1, apiError2);
        assertEquals(apiError1.hashCode(), apiError2.hashCode());
        
        assertNotEquals(apiError1, apiError3);
        assertNotEquals(apiError1.hashCode(), apiError3.hashCode());
        
        assertNotEquals(apiError1, null);
        assertNotEquals(apiError1, new Object());
    }

    @Test
    void testToString() {
        // Arrange
        HttpStatus status = HttpStatus.BAD_REQUEST;
        LocalDateTime timestamp = LocalDateTime.now();
        String message = "Erro de validação";
        String debugMessage = "Campo obrigatório não informado";
        List<ApiSubError> subErrors = new ArrayList<>();
        subErrors.add(new ApiValidationError("objeto", "campo", "valor", "mensagem"));
        
        ApiError apiError = new ApiError(status, timestamp, message, debugMessage, subErrors);
        
        // Act
        String toStringResult = apiError.toString();
        
        // Assert
        assertTrue(toStringResult.contains("status=" + status));
        assertTrue(toStringResult.contains("message=" + message));
        assertTrue(toStringResult.contains("debugMessage=" + debugMessage));
        assertTrue(toStringResult.contains("subErrors=" + subErrors));
    }
}
