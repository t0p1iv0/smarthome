package com.toplivo.home_service.config

import com.toplivo.home_service.dto.ErrorDto
import com.toplivo.home_service.exception.ApiException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    private val log = KotlinLogging.logger {  }

    @ExceptionHandler(ApiException::class)
    fun handleApiException(ex: ApiException): ResponseEntity<ErrorDto> {
        log.debug(ex) { ex.message }
        return ResponseEntity.status(ex.httpStatus).body(
            ErrorDto(code = ex.code, message = ex.message)
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorDto> {
        log.debug(ex) { ex.message }
        val error = ErrorDto(
            code = "INTERNAL_EXCEPTION",
            message = ex.message ?: "Что-то пошло не так"
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error)
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