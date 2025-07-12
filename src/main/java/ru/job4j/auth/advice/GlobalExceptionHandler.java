package ru.job4j.auth.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

  private final ObjectMapper objectMapper;

  public GlobalExceptionHandler(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(value = {NullPointerException.class})
  public void handleNPException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setContentType("application/json");
    response.getWriter()
        .write(
            objectMapper.writeValueAsString(
              Map.of(
                  "message", "Some of fields empty",
                  "details", e.getMessage()
              )
            )
        );
    log.error(e.getMessage(), e);
  }

  @ExceptionHandler(value = {NoSuchElementException.class, UsernameNotFoundException.class})
  public void handleNFException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setStatus(HttpStatus.NOT_FOUND.value());
    response.setContentType("application/json");
    response.getWriter()
        .write(
            objectMapper.writeValueAsString(
                Map.of(
                    "message", "Object not found.",
                    "details", e.getMessage()
                )
            )
        );
    log.error(e.getMessage(), e);
  }
}
