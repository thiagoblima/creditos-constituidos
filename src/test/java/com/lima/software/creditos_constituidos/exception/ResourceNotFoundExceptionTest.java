package com.lima.software.creditos_constituidos.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

    @Test
    void testConstructorWithMessage() {
        // Arrange
        String errorMessage = "Recurso não encontrado";
        
        // Act
        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);
        
        // Assert
        assertEquals(errorMessage, exception.getMessage());
    }
    
    @Test
    void testExceptionInheritance() {
        // Arrange & Act
        ResourceNotFoundException exception = new ResourceNotFoundException("Teste");
        
        // Assert
        assertTrue(exception instanceof RuntimeException);
    }
    
    @Test
    void testResponseStatusAnnotation() {
        // Arrange & Act
        ResponseStatus annotation = ResourceNotFoundException.class.getAnnotation(ResponseStatus.class);
        
        // Assert
        assertNotNull(annotation);
        assertEquals(HttpStatus.NOT_FOUND, annotation.value());
    }
}
