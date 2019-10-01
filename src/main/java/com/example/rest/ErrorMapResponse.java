package com.example.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ErrorMapResponse<T> {

    private Map<T, T> constraintViolations;

    public ErrorMapResponse() {
    }

    ErrorMapResponse(Map<T, T> errors) {
        this.constraintViolations = errors;
    }

    public Map<T, T> getConstraintViolations() {
        return constraintViolations;
    }


}
