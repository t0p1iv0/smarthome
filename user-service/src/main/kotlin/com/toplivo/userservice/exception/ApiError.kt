package com.toplivo.userservice.exception

import org.springframework.http.HttpStatus

enum class ApiError(
    private val httpStatus: HttpStatus,
    private val message: String
) {
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "Token not found or ttl expired"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "Invalid username or password"),
    PASSWORDS_DOES_NOT_MATCH(HttpStatus.BAD_REQUEST, "Passwords does not match"),
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "Username unavailable");


    fun toException() = ApiException(
        httpStatus = httpStatus,
        code = name,
        message = message
    )
}