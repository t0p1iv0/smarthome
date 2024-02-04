package com.toplivo.userservice.config

import com.toplivo.userservice.dto.ErrorDto
import com.toplivo.userservice.exception.ApiException
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    private val logger = KotlinLogging.logger {  }

    @ExceptionHandler(ApiException::class)
    fun handleApiException(ex: ApiException): ResponseEntity<ErrorDto> {
        logger.debug(ex) { ex.message }
        return ResponseEntity.status(ex.httpStatus).body(
            ErrorDto(code = ex.code, message = ex.message)
        )
    }
}