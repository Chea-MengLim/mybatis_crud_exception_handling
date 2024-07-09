package org.example.mybatis_crud_exception_handling.exception;

import org.example.mybatis_crud_exception_handling.model.Venue;
import org.example.mybatis_crud_exception_handling.model.dto.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

        for(var fieldError : ex.getBindingResult().getFieldErrors()){
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Bad Request");
        problemDetail.setProperty("errors", errors);
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ProblemDetail handleMethodValidationException(HandlerMethodValidationException ex){
        Map<String, String> errors = new HashMap<>();
        for(var parameterError : ex.getAllValidationResults()){
            String parameterName = parameterError.getMethodParameter().getParameterName();
            for(var error : parameterError.getResolvableErrors()){
                errors.put(parameterName, error.getDefaultMessage());
            }
        }
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Bad Request");
        problemDetail.setProperty("errors", errors);
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        problemDetail.setTitle("Not Found");
        problemDetail.setProperty("Datetime", LocalDateTime.now());
        return problemDetail;
    }


}
