package com.toplivo.apigateway.config

import com.toplivo.apigateway.exception.ApiException
import com.toplivo.apigateway.exception.ErrorDto
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    private val log = KotlinLogging.logger {  }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorDto> {
        log.debug(ex) { ex.message }
        val error = ErrorDto(
            code = "INTERNAL_EXCEPTION",
            message = ex.message ?: "Что-то пошло не так"
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error)
    }

    @ExceptionHandler(ApiException::class)
    fun handleApiException(ex: ApiException): ResponseEntity<ErrorDto> {
        log.debug(ex) { ex.message }
        return ResponseEntity.status(ex.httpStatus).body(
            ErrorDto(code = ex.code, message = ex.message)
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorDto> {
        log.debug(ex) { ex.message }
        val error = ErrorDto(
            code = "REQUEST_VALIDATION_ERROR",
            message = ex.message
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error)
    }
}