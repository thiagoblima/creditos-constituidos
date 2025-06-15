package com.lima.software.creditos_constituidos.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiValidationErrorTest {

    @Test
    void testNoArgsConstructor() {
        // Act
        ApiValidationError error = new ApiValidationError();
        
        // Assert
        assertNotNull(error);
        assertNull(error.getObject());
        assertNull(error.getField());
        assertNull(error.getRejectedValue());
        assertNull(error.getMessage());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String object = "usuario";
        String field = "email";
        Object rejectedValue = "email-invalido";
        String message = "Formato de email inválido";
        
        // Act
        ApiValidationError error = new ApiValidationError(object, field, rejectedValue, message);
        
        // Assert
        assertEquals(object, error.getObject());
        assertEquals(field, error.getField());
        assertEquals(rejectedValue, error.getRejectedValue());
        assertEquals(message, error.getMessage());
    }

    @Test
    void testPartialConstructor() {
        // Arrange
        String object = "produto";
        String message = "Nome do produto é obrigatório";
        
        // Act
        ApiValidationError error = new ApiValidationError(object, message);
        
        // Assert
        assertEquals(object, error.getObject());
        assertEquals(message, error.getMessage());
        assertNull(error.getField());
        assertNull(error.getRejectedValue());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        ApiValidationError error = new ApiValidationError();
        String object = "cliente";
        String field = "cpf";
        Object rejectedValue = "123.456.789-00";
        String message = "CPF inválido";
        
        // Act
        error.setObject(object);
        error.setField(field);
        error.setRejectedValue(rejectedValue);
        error.setMessage(message);
        
        // Assert
        assertEquals(object, error.getObject());
        assertEquals(field, error.getField());
        assertEquals(rejectedValue, error.getRejectedValue());
        assertEquals(message, error.getMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        ApiValidationError error1 = new ApiValidationError("usuario", "email", "email-invalido", "Formato de email inválido");
        ApiValidationError error2 = new ApiValidationError("usuario", "email", "email-invalido", "Formato de email inválido");
        ApiValidationError error3 = new ApiValidationError("produto", "nome", null, "Nome é obrigatório");
        
        // Act & Assert
        assertEquals(error1, error2);
        assertEquals(error1.hashCode(), error2.hashCode());
        
        assertNotEquals(error1, error3);
        assertNotEquals(error1.hashCode(), error3.hashCode());
        
        assertNotEquals(error1, null);
        assertNotEquals(error1, new Object());
    }

    @Test
    void testToString() {
        // Arrange
        ApiValidationError error = new ApiValidationError("usuario", "email", "email-invalido", "Formato de email inválido");
        
        // Act
        String toStringResult = error.toString();
        
        // Assert
        assertTrue(toStringResult.contains("object=usuario"));
        assertTrue(toStringResult.contains("field=email"));
        assertTrue(toStringResult.contains("rejectedValue=email-invalido"));
        assertTrue(toStringResult.contains("message=Formato de email inválido"));
    }
    
    @Test
    void testInheritanceFromApiSubError() {
        // Arrange & Act
        ApiValidationError error = new ApiValidationError();
        
        // Assert
        assertTrue(error instanceof ApiSubError);
    }
}
