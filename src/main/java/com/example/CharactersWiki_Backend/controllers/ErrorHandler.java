package com.example.CharactersWiki_Backend.controllers;

import com.example.CharactersWiki_Backend.models.dataTransferObjects.ErrorResponse;
import com.example.CharactersWiki_Backend.models.dataTransferObjects.ErrorsResponse;
import com.example.CharactersWiki_Backend.models.errors.BadRequestException;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.List;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(NotFoundException exception)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler({
            BadRequestException.class,
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            HttpMessageNotReadableException.class,
            IllegalArgumentException.class,
            MaxUploadSizeExceededException.class
    })
    public ResponseEntity<ErrorsResponse> handleBadRequestExceptions(Exception exception)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(
            switch (exception) {
                case BadRequestException badRequestException ->
                        new ErrorsResponse(List.of(badRequestException.getMessage()));
                case ConstraintViolationException constraintViolationException ->
                        new ErrorsResponse(constraintViolationException.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList());
                case MethodArgumentNotValidException methodArgumentNotValidException ->
                        new ErrorsResponse(methodArgumentNotValidException.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
                case MethodArgumentTypeMismatchException methodArgumentTypeMismatchException ->
                        new ErrorsResponse(List.of("Value for parameter \"${error.name}\" has wrong type."));
                case MissingServletRequestParameterException missingServletRequestParameterException ->
                        new ErrorsResponse(List.of("Value for parameter \"${error.parameterName}\" is missing."));
                case HttpMessageNotReadableException httpMessageNotReadableException ->
                        new ErrorsResponse(List.of("Request body is missing or has wrong format."));
                case IllegalArgumentException illegalArgumentException ->
                        new ErrorsResponse(List.of("Some validation failed."));
                case MaxUploadSizeExceededException maxUploadSizeExceededException ->
                        new ErrorsResponse(List.of("File size exceeds the limit of 5 MB."));
                default -> throw new IllegalStateException("Unexpected error occurred.");
            });
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupportedExceptions(HttpRequestMethodNotSupportedException exception)
    {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).contentType(MediaType.APPLICATION_JSON).body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception exception)
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(new ErrorResponse(exception.getMessage()));
    }
}