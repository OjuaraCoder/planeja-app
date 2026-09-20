package com.ojuara.planeja.infra.handlers;

import com.ojuara.planeja.common.exceptions.ValidationException;
import com.ojuara.planeja.common.validation.CampoInvalido;
import com.ojuara.planeja.common.validation.RegistroNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e) {
        var status = HttpStatus.UNPROCESSABLE_CONTENT;
        var body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", e.getMessage(),
                "camposInvalidos", e.getCamposInvalidos()
        );
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var camposInvalidos = e.getFieldErrors().stream().map(fieldError -> new CampoInvalido(fieldError.getField(), fieldError.getDefaultMessage())).toList();
        var status = HttpStatus.UNPROCESSABLE_CONTENT;

        var body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", e.getMessage(),
                "camposInvalidos", camposInvalidos
        );
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public ResponseEntity<?> handleRegistroNaoEncontradoException(RegistroNaoEncontradoException e) {
        var status = HttpStatus.NOT_FOUND;
        var body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", e.getMessage(),
                "message", e.getMessage()
        );
        return ResponseEntity.status(status).body(body);
    }

}
