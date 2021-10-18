package pl.kurs.veterinaryclinic.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pl.kurs.veterinaryclinic.exceptions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoEntityException.class, WrongIdException.class})
    public ResponseEntity<ExceptionResponse> globallyHandleException(Exception e) {
        ExceptionResponse response =
                new ExceptionResponse(Arrays.asList(e.getMessage()), "404 NOT FOUND", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler({DuplicateNipException.class, VisitException.class, DateException.class})
    public ResponseEntity<ExceptionResponse> handleCustomValidationException(Exception e) {
        ExceptionResponse response =
                new ExceptionResponse(Arrays.asList(e.getMessage()), "400 BAD REQUEST", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionResponse> handleCustomValidationException(MethodArgumentNotValidException e) {
        List<String> messages = e.getFieldErrors().stream()
                .map(fe -> "Field: " + fe.getField() + ", rejected value: " + fe.getRejectedValue()
                        + ", message: " + fe.getDefaultMessage())
                .collect(Collectors.toList());

        ExceptionResponse response =
                new ExceptionResponse(messages, "400 BAD REQUEST", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


}
