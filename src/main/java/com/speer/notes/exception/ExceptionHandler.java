package com.speer.notes.exception;

import com.speer.notes.dtos.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path(request.getRequestURI())
                .message(errors)
                .build();
        return new ResponseEntity<ErrorResponseDTO>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentTypeMismatchException.class, BadRequestException.class})
    public ResponseEntity<ErrorResponseDTO> handleBadRequestException(Exception e, HttpServletRequest request) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path(request.getRequestURI())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorResponseDTO> handleNotFoundException(Exception e, HttpServletRequest request) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path(request.getRequestURI())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({UnauthorizedActionException.class})
    public ResponseEntity<ErrorResponseDTO> handleUnauthorizedException(Exception e, HttpServletRequest request) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .path(request.getRequestURI())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.UNAUTHORIZED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({UserAlreadyExistException.class})
    public ResponseEntity<ErrorResponseDTO> handleConflictException(Exception e, HttpServletRequest request) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .path(request.getRequestURI())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({InternalServerException.class})
    public ResponseEntity<ErrorResponseDTO> handleInternalServerException(Exception e, HttpServletRequest request) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .path(request.getRequestURI())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadableException(Exception e, HttpServletRequest request) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path(request.getRequestURI())
                .message("Invalid type of " + e.getMessage().substring(e.getMessage().lastIndexOf("[\"")+2,e.getMessage().lastIndexOf("\"]")) + ".")
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorResponseDTO> handleHttpRequestMethodNotSupportedException(Exception e, HttpServletRequest request) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .error(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
                .path(request.getRequestURI())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorResponseDTO> handleForbiddenException(Exception e, HttpServletRequest request) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .path(request.getRequestURI())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.FORBIDDEN);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({MissingServletRequestPartException.class})
    public ResponseEntity<ErrorResponseDTO> handleMissingServletRequestPartException(Exception e, HttpServletRequest request) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path(request.getRequestURI())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorResponseDTO> handleMissingServletRequestParameterException(Exception e, HttpServletRequest request) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path(request.getRequestURI())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(Exception e, HttpServletRequest request) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path(request.getRequestURI())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

}
