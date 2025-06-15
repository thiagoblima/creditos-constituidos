package com.lima.software.creditos_constituidos.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

class BadRequestExceptionTest {

    @Test
    void testConstructorWithMessage() {
        // Arrange
        String errorMessage = "Dados inválidos na requisição";
        
        // Act
        BadRequestException exception = new BadRequestException(errorMessage);
        
        // Assert
        assertEquals(errorMessage, exception.getMessage());
    }
    
    @Test
    void testExceptionInheritance() {
        // Arrange & Act
        BadRequestException exception = new BadRequestException("Teste");
        
        // Assert
        assertTrue(exception instanceof RuntimeException);
    }
    
    @Test
    void testResponseStatusAnnotation() {
        // Arrange & Act
        ResponseStatus annotation = BadRequestException.class.getAnnotation(ResponseStatus.class);
        
        // Assert
        assertNotNull(annotation);
        assertEquals(HttpStatus.BAD_REQUEST, annotation.value());
    }
}
