package com.example.property.exception;

import com.example.property.exception.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    ExceptionMessages exceptionMessages = new ExceptionMessages();


    @ExceptionHandler(CreateJsonException.class)
    protected ResponseEntity<?> handleCreateJsonException(CreateJsonException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exceptionMessages.getCreateJsonExceptionMessage(), "", 500);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(NotAuthorizedException.class)
    protected ResponseEntity<?> handleNotAuthorizedException(NotAuthorizedException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exceptionMessages.getNotAuthorizedExceptionMessage(), "", 401);
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    protected ResponseEntity<?> handleItemNotFoundException(ItemNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exceptionMessages.getItemNotFoundMessage(), "", 404);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TypeNotFoundException.class)
    protected ResponseEntity<?> handleTypeNotFoundException(TypeNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exceptionMessages.getTypeNotFoundMessage(), "", 404);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exceptionMessages.getUserNotFoundMessage(), "", 404);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exceptionMessages.getAccessDeniedExceptionMessage(), "", 403);
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ParseException.class)
    protected ResponseEntity<?> handleParseException(ParseException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exceptionMessages.getParseExceptionMessage(), "", 500);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<?> handleNullPointerException(NullPointerException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exceptionMessages.getNullPointerExceptionMessage(), "", 500);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<?> handleIOException(IOException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exceptionMessages.getIOExceptionMessage(), "", 500);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
