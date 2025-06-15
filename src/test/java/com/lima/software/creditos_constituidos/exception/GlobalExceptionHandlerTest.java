package com.lima.software.creditos_constituidos.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @Mock
    private WebRequest webRequest;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private HttpMessageNotReadableException httpMessageNotReadableException;

    @Mock
    private MethodArgumentTypeMismatchException methodArgumentTypeMismatchException;

    @Mock
    private ConstraintViolationException constraintViolationException;

    @Mock
    private ConstraintViolation<?> constraintViolation;

    @Mock
    private DataIntegrityViolationException dataIntegrityViolationException;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleResourceNotFound_shouldReturnNotFoundStatus() {
        // Arrange
        ResourceNotFoundException ex = new ResourceNotFoundException("Recurso não encontrado");

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleResourceNotFound(ex);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ApiError error = (ApiError) response.getBody();
        assertNotNull(error);
        assertEquals("Recurso não encontrado", error.getMessage());
    }

    @Test
    void handleBadRequest_shouldReturnBadRequestStatus() {
        // Arrange
        BadRequestException ex = new BadRequestException("Requisição inválida");

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleBadRequest(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ApiError error = (ApiError) response.getBody();
        assertNotNull(error);
        assertEquals("Requisição inválida", error.getMessage());
    }

    @Test
    void handleDataIntegrityViolation_shouldReturnConflictStatus() {
        // Arrange
        Throwable cause = new RuntimeException("Violação de chave única");
        when(dataIntegrityViolationException.getMostSpecificCause()).thenReturn(cause);

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleDataIntegrityViolation(dataIntegrityViolationException);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        ApiError error = (ApiError) response.getBody();
        assertNotNull(error);
        assertEquals("Violação de integridade de dados", error.getMessage());
        assertEquals("Violação de chave única", error.getDebugMessage());
    }

    @Test
    void handleConstraintViolation_shouldReturnBadRequestStatus() {
        // Arrange
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(constraintViolation);
        
        when(constraintViolationException.getConstraintViolations()).thenReturn(violations);
        when(constraintViolation.getRootBeanClass()).thenReturn((Class) Object.class);
        when(constraintViolation.getPropertyPath()).thenReturn(mock(jakarta.validation.Path.class));
        when(constraintViolation.getPropertyPath().toString()).thenReturn("nome");
        when(constraintViolation.getInvalidValue()).thenReturn("");
        when(constraintViolation.getMessage()).thenReturn("não pode estar em branco");

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleConstraintViolation(constraintViolationException);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ApiError error = (ApiError) response.getBody();
        assertNotNull(error);
        assertEquals("Erro de validação", error.getMessage());
        assertNotNull(error.getSubErrors());
        assertEquals(1, error.getSubErrors().size());
        
        ApiValidationError subError = (ApiValidationError) error.getSubErrors().get(0);
        assertEquals("Object", subError.getObject());
        assertEquals("nome", subError.getField());
        assertEquals("", subError.getRejectedValue());
        assertEquals("não pode estar em branco", subError.getMessage());
    }

    @Test
    void handleMethodArgumentTypeMismatch_shouldReturnBadRequestStatus() {
        // Arrange
        when(methodArgumentTypeMismatchException.getName()).thenReturn("id");
        when(methodArgumentTypeMismatchException.getValue()).thenReturn("abc");
        when(methodArgumentTypeMismatchException.getRequiredType()).thenReturn((Class) Long.class);
        when(methodArgumentTypeMismatchException.getMessage()).thenReturn("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'");

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleMethodArgumentTypeMismatch(methodArgumentTypeMismatchException);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ApiError error = (ApiError) response.getBody();
        assertNotNull(error);
        assertEquals("O parâmetro 'id' com valor 'abc' não pôde ser convertido para o tipo 'Long'", error.getMessage());
        assertEquals("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'", error.getDebugMessage());
    }

    @Test
    void handleMethodArgumentNotValid_shouldReturnBadRequestStatus() {
        // Arrange
        List<FieldError> fieldErrors = List.of(
            new FieldError("usuario", "nome", null, false, null, null, "não pode estar em branco"),
            new FieldError("usuario", "email", "email-invalido", false, null, null, "formato inválido")
        );
        
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleMethodArgumentNotValid(
            methodArgumentNotValidException, 
            new HttpHeaders(), 
            HttpStatus.BAD_REQUEST, 
            webRequest
        );

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ApiError error = (ApiError) response.getBody();
        assertNotNull(error);
        assertEquals("Erro de validação", error.getMessage());
        assertNotNull(error.getSubErrors());
        assertEquals(2, error.getSubErrors().size());
    }

    @Test
    void handleHttpMessageNotReadable_shouldReturnBadRequestStatus() {
        // Arrange
        when(httpMessageNotReadableException.getMessage()).thenReturn("JSON parse error");

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleHttpMessageNotReadable(
            httpMessageNotReadableException, 
            new HttpHeaders(), 
            HttpStatus.BAD_REQUEST, 
            webRequest
        );

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ApiError error = (ApiError) response.getBody();
        assertNotNull(error);
        assertEquals("JSON malformado", error.getMessage());
        assertEquals("JSON parse error", error.getDebugMessage());
    }

    @Test
    void handleAllExceptions_shouldReturnInternalServerErrorStatus() {
        // Arrange
        Exception ex = new RuntimeException("Erro inesperado");

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleAllExceptions(ex);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ApiError error = (ApiError) response.getBody();
        assertNotNull(error);
        assertEquals("Ocorreu um erro interno no servidor", error.getMessage());
        assertEquals("Erro inesperado", error.getDebugMessage());
    }
}
