package com.viniciusdev.spring_security_architecture.infra;

import com.viniciusdev.spring_security_architecture.dtos.error.FieldValidationError;
import com.viniciusdev.spring_security_architecture.exceptions.EmailAlreadyExistsException;
import com.viniciusdev.spring_security_architecture.exceptions.InvalidCredentialsException;
import com.viniciusdev.spring_security_architecture.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(e.getStatus(), e.getMessage());
        pd.setTitle("Resource not found");
        pd.setType(URI.create("http://localhost:8080/problems/not-found"));
        pd.setInstance(URI.create(request.getRequestURI()));
        pd.setProperties(Map.of("error", e.getCode(), "timestamp", Instant.now()));
        return pd;
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ProblemDetail handleEmailAlreadyExists(EmailAlreadyExistsException e, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(e.getStatus(), e.getMessage());
        pd.setTitle("Body with bad requests");
        pd.setType(URI.create("http://localhost:8080/problems/email-already-exists"));
        pd.setInstance(URI.create(request.getRequestURI()));
        pd.setProperties(Map.of("error", e.getCode(), "timestamp", Instant.now()));
        return pd;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        pd.setTitle("Data integrity violation");
        pd.setType(URI.create("http://localhost:8080/problems/data-integrity-violation"));
        pd.setInstance(URI.create(request.getRequestURI()));
        pd.setProperties(Map.of("error", HttpStatus.CONFLICT.name(), "timestamp", Instant.now()));
        return pd;
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ProblemDetail handleInvalidCredentialsException(InvalidCredentialsException e, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(e.getStatus(), e.getMessage());
        pd.setTitle("Invalid credentials");
        pd.setType(URI.create("http://localhost:8080/problems/invalid-credentials"));
        pd.setInstance(URI.create(request.getRequestURI()));
        pd.setProperties(Map.of("error", e.getCode(), "timestamp", Instant.now()));
        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {

        List<FieldValidationError> fieldErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> new FieldValidationError(
                        fe.getField(),
                        fe.getDefaultMessage()
                ))
                .toList();

        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "One or more fields are invalid");
        pd.setTitle("Invalid fields in request");
        pd.setType(URI.create("http://localhost:8080/problems/invalid-fields"));
        pd.setInstance(URI.create(request.getRequestURI()));
        pd.setProperties(Map.of("error", HttpStatus.BAD_REQUEST.name(), "timestamp", Instant.now(), "fieldErrors", fieldErrors));

        return pd;
    }
}
