package com.example.rest;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler {

    /***
     *
     * ConstraintViolationException handler
     *
     * @param ex exception
     * @return responseEntity
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> failedParamAndMessagePairs = new HashMap<>();

        for (ConstraintViolation<?> next : ex.getConstraintViolations()) {
            failedParamAndMessagePairs.put(((PathImpl) next.getPropertyPath())
                    .getLeafNode().getName(), next.getMessage());
        }
        return new ResponseEntity<>(new ErrorMapResponse<>(failedParamAndMessagePairs), BAD_REQUEST);
    }

    /***
     *
     * MethodArgumentTypeMismatchException handler
     *
     * @param ex exception
     * @return responseEntity
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<?> handle(MethodArgumentTypeMismatchException ex) {
        Map<String, String> failedParamAndMessagePairs = new HashMap<>();
        failedParamAndMessagePairs.put(ex.getName(), "Wrong argument type! Got " + ex.getValue().getClass().toString() + " Expecte "
                + ex.getRequiredType().toString());

        return new ResponseEntity<>(new ErrorMapResponse<>(failedParamAndMessagePairs), BAD_REQUEST);
    }

    /***
     *
     * HttpMessageNotReadableException handler
     *
     * @param ex exception
     * @return responseEntity
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    protected ResponseEntity<?> handle(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(ex.getMessage(), BAD_REQUEST);
    }

    /***
     *
     * MethodArgumentNotValidException handler
     *
     * @param ex exception
     * @return responseEntity
     */
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private ResponseEntity processFieldErrors(List<org.springframework.validation.FieldError> fieldErrors) {
        Map<String, String> failedParamAndMessagePairs = new HashMap<>();
        for (org.springframework.validation.FieldError fieldError : fieldErrors) {
            failedParamAndMessagePairs.put((fieldError.getField())
                    , fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(new ErrorMapResponse<>(failedParamAndMessagePairs), BAD_REQUEST);
    }
}

