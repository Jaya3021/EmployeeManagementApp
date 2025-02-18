package com.singh.employeemanagement.controller.advice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.singh.employeemanagement.dto.ErrorResponseDTO;
import com.singh.employeemanagement.exception.BadRequestException;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<ErrorResponseDTO> badRequest(MethodArgumentNotValidException ex) {
        List<String> messages = new ArrayList<>();
        for (ObjectError error : ex.getAllErrors())
            messages.add(error.getDefaultMessage());
        return new ResponseEntity<>(new ErrorResponseDTO(messages, HttpStatus.BAD_REQUEST.value(), new Date()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { BadRequestException.class, IllegalArgumentException.class })
    public ResponseEntity<ErrorResponseDTO> badRequest(Exception ex) {
        List<String> messages = new ArrayList<>();
        messages.add(ex.getMessage());
        return new ResponseEntity<>(new ErrorResponseDTO(messages, HttpStatus.BAD_REQUEST.value(), new Date()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ErrorResponseDTO> generalException(Exception ex) {
        List<String> messages = new ArrayList<>();
        messages.add(ex.getMessage());
        return new ResponseEntity<>(
                new ErrorResponseDTO(messages, HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
