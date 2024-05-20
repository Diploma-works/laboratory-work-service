package com.vko.labworkproducer.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler {

    @ExceptionHandler(LabWorkNotFoundException.class)
    public ResponseEntity<String> handleLabWorkNotFoundException(LabWorkNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LabWorkAlreadyExistsException.class)
    public ResponseEntity<String> handleLabWorkAlreadyExistsException(LabWorkAlreadyExistsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UsernameOrPasswordException.class)
    public ResponseEntity<String> handleUsernameOrPasswordException(UsernameOrPasswordException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(TopicAlreadyExistsException.class)
    public ResponseEntity<String> handleTopicAlreadyExistsException(TopicAlreadyExistsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(TopicUsedInLabWorkException.class)
    public ResponseEntity<String> handleTopicUsedInLabWorkException(TopicUsedInLabWorkException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
