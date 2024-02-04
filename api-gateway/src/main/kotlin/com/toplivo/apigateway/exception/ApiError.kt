package com.toplivo.apigateway.exception

import org.springframework.http.HttpStatus

enum class ApiError(
    private val httpStatus: HttpStatus,
    private val message: String
) {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token invalid"),
    UNAUTHORIZED_REQUEST(HttpStatus.FORBIDDEN, "Access denied");

    fun toException() = ApiException(
        httpStatus = httpStatus,
        code = name,
        message = message
    )
}