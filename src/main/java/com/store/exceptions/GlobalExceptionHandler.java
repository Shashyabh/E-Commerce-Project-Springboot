package com.store.exceptions;

import com.store.dtos.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        //logger.info("Exception handler invoked");
        ApiResponse response = ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> methodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        Map<String,Object> response=new HashMap<>();
        allErrors.stream().forEach(objectError -> {
            String defaultMessage = objectError.getDefaultMessage();
            String field = ((FieldError) objectError).getField();
            response.put(field,defaultMessage);
        });
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    // Handle Bad API request exception
    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponse> badApiRequestExceptionHandler(BadApiRequest ex){
        ApiResponse response = ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
