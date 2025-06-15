package com.lima.software.creditos_constituidos.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiSubErrorTest {

    // Classe concreta para testar a classe abstrata ApiSubError
    private static class ConcreteApiSubError extends ApiSubError {
        // Não precisa implementar nada, pois ApiSubError já tem todos os métodos necessários
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        ApiSubError apiSubError = new ApiValidationError();
        
        // Assert
        assertNotNull(apiSubError);
        assertNull(apiSubError.getField());
        assertNull(apiSubError.getMessage());
        assertNull(apiSubError.getRejectedValue());
        assertNull(apiSubError.getReason());
        assertNull(apiSubError.getCode());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String object = "nome";
        String message = "Campo obrigatório";
        
        // Act
        ApiValidationError apiSubError = new ApiValidationError(object, message);
        
        // Assert
        assertEquals(object, apiSubError.getObject());
        assertEquals(message, apiSubError.getMessage());
        assertNull(apiSubError.getField());
        assertNull(apiSubError.getRejectedValue());
        assertNull(apiSubError.getReason());
        assertNull(apiSubError.getCode());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        ApiSubError apiSubError = new ApiValidationError();
        String field = "nome";
        String message = "Campo obrigatório";
        Object rejectedValue = "";
        String reason = "Campo não pode ser vazio";
        String code = "FIELD_REQUIRED";
        
        // Act
        apiSubError.setField(field);
        apiSubError.setMessage(message);
        apiSubError.setRejectedValue(rejectedValue);
        apiSubError.setReason(reason);
        apiSubError.setCode(code);
        
        // Assert
        assertEquals(field, apiSubError.getField());
        assertEquals(message, apiSubError.getMessage());
        assertEquals(rejectedValue, apiSubError.getRejectedValue());
        assertEquals(reason, apiSubError.getReason());
        assertEquals(code, apiSubError.getCode());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        ApiSubError apiSubError1 = new ApiValidationError(
            "nome", "Campo obrigatório");
        
        ApiSubError apiSubError2 = new ApiValidationError(
            "nome", "Campo obrigatório");
        
        ApiSubError apiSubError3 = new ApiValidationError(
            "email", "Formato inválido");
        
        // Act & Assert
        assertEquals(apiSubError1, apiSubError2);
        assertEquals(apiSubError1.hashCode(), apiSubError2.hashCode());
        
        assertNotEquals(apiSubError1, apiSubError3);
        assertNotEquals(apiSubError1.hashCode(), apiSubError3.hashCode());
        
        assertNotEquals(apiSubError1, null);
        assertNotEquals(apiSubError1, new Object());
    }

    @Test
    void testToString() {
        // Arrange
        ApiSubError apiSubError = new ApiValidationError(
            "nome", "Campo obrigatório");
        
        // Act
        String toStringResult = apiSubError.toString();
        
        // Assert
        assertTrue(toStringResult.contains("object=nome"));
        assertTrue(toStringResult.contains("message=Campo obrigatório"));
        // Não devemos verificar campos que não foram definidos ou que são nulos
        assertFalse(toStringResult.contains("reason=Campo não pode ser vazio"));
        assertFalse(toStringResult.contains("code=FIELD_REQUIRED"));
    }
}
