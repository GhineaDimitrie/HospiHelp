package com.hospihelp.hospihelp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Eroare generica - RuntimeException (aruncata de service-uri)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(
            RuntimeException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // Resursa negasita - 404
    @ExceptionHandler(jakarta.persistence.EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(
            jakarta.persistence.EntityNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Acces interzis - 403
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(
            AccessDeniedException ex) {
        return buildResponse(HttpStatus.FORBIDDEN,
                "Nu ai permisiunea sa accesezi aceasta resursa");
    }

    // Credentiale gresite - 401
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(
            BadCredentialsException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED,
                "Email sau parola incorecte");
    }

    // Argument invalid
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(
            IllegalArgumentException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // Orice alta eroare neasteptata - 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(
            Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "A aparut o eroare interna. Te rugam sa incerci din nou.");
    }

    // Metoda ajutatoare care construieste raspunsul standard
    private ResponseEntity<Map<String, Object>> buildResponse(
            HttpStatus status, String mesaj) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("eroare", status.getReasonPhrase());
        body.put("mesaj", mesaj);
        return ResponseEntity.status(status).body(body);
    }
}