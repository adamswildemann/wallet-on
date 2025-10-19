package br.com.walleton.exception.handler;

import br.com.walleton.exception.ExceptionResponse;
import br.com.walleton.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidation(MethodArgumentNotValidException ex, WebRequest req) {
        var body = new ExceptionResponse(
                OffsetDateTime.now(),
                "Requisição inválida!",
                req.getDescription(false)
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConflict(DataIntegrityViolationException ex, WebRequest req) {
        var body = new ExceptionResponse(
                OffsetDateTime.now(),
                "Os campos devem ser únicos!",
                req.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleBusiness(IllegalArgumentException ex, WebRequest req) {
        var body = new ExceptionResponse(
                OffsetDateTime.now(),
                ex.getMessage(),
                req.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(ResourceNotFoundException ex, WebRequest req) {
        var body = new ExceptionResponse(
                OffsetDateTime.now(),
                ex.getMessage(),
                req.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

}
