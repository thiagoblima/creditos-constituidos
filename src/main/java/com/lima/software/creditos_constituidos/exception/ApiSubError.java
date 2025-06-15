package com.lima.software.creditos_constituidos.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ApiSubError {
    private String field;
    private String message;
    private Object rejectedValue;
    private String reason;
    private String code;
}
