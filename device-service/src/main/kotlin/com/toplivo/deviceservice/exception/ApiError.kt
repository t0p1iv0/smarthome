package com.toplivo.deviceservice.exception

import org.springframework.http.HttpStatus

enum class ApiError(
    private val httpStatus: HttpStatus,
    private val message: String
) {
    INVALID_TUYA_DEVICE_ID(HttpStatus.BAD_REQUEST, "Device not found in Tuya"),
    DEVICE_NOT_FOUND(HttpStatus.NOT_FOUND, "Device not found in database"),
    DEVICE_PARSE_EXCEPTION(HttpStatus.NOT_FOUND, "Unable to request device information from Tuya"),
    CAPABILITY_PARSE_EXCEPTION(HttpStatus.NOT_FOUND, "Unable to request device status from Tuya");


    fun toException() = ApiException(
        httpStatus = httpStatus,
        code = name,
        message = message
    )
}