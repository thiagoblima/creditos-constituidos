package com.lima.software.creditos_constituidos.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private HttpStatus status;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;
    
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;
    
    public ApiError(HttpStatus status) {
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
    
    public ApiError(HttpStatus status, String message) {
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.message = message;
    }
    
    public ApiError(HttpStatus status, String message, Throwable ex) {
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}
