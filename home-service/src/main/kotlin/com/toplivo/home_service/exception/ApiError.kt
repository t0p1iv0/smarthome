package com.toplivo.home_service.exception

import org.springframework.http.HttpStatus

enum class ApiError(
    private val httpStatus: HttpStatus,
    private val message: String
) {
    HOME_NOT_FOUND(HttpStatus.NOT_FOUND, "Дом не найден"),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "Комната не найдена");

    fun toException() = ApiException(
        httpStatus = httpStatus,
        code = name,
        message = message
    )
}
