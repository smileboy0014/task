package org.example.multimodule.core.common.exceptions.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.multimodule.core.common.enums.ErrorCode;
import org.example.multimodule.core.common.exceptions.CustomException;
import org.example.multimodule.core.common.exceptions.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.example.multimodule.core.common.enums.ErrorCode.DUPLICATE_RESOURCE;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponse> handleDataException() {
        log.error("handleDataException throw Exception : {}", DUPLICATE_RESOURCE);
        return ErrorResponse.toResponseEntity(DUPLICATE_RESOURCE, "");
    }

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        log.error("handleRuntimeException throw RuntimeException : {}", e.getMessage());
        return ErrorResponse.toResponseEntity(ErrorCode.RUNTIME, e.getMessage());
    }
}
