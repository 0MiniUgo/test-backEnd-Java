package com.hugo.UOLJogos.controller.exceptions;

import com.hugo.UOLJogos.service.exceptions.DatabaseUniqueException;
import com.hugo.UOLJogos.service.exceptions.NoVacancyException;
import com.hugo.UOLJogos.service.exceptions.PlayerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<StandardError> playerNotFound(PlayerNotFoundException ex, HttpServletRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError error =
                new StandardError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(NoVacancyException.class)
    public ResponseEntity<StandardError> noVacancy(NoVacancyException ex, HttpServletRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error =
                new StandardError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DatabaseUniqueException.class)
    public ResponseEntity<StandardError> databaseUnique(DatabaseUniqueException ex, HttpServletRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error =
                new StandardError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }
}
