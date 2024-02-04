package com.toplivo.deviceservice.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.toplivo.deviceservice.dto.capabilities.Capability
import com.toplivo.deviceservice.exception.ApiError
import org.springframework.stereotype.Service

@Service
class ParseService {
    fun parseDeviceName(tuyaDevice: String) =
        jacksonObjectMapper().readTree(tuyaDevice)
            .findValue("name")
            ?.asText()
            ?: throw ApiError.DEVICE_PARSE_EXCEPTION.toException()

    fun parseDeviceCategory(tuyaDevice: String) =
        jacksonObjectMapper().readTree(tuyaDevice)
            .findValue("category")
            ?.asText()
            ?: throw ApiError.DEVICE_PARSE_EXCEPTION.toException()
    fun parseDeviceCapabilities(tuyaDeviceStatus: String) =
        jacksonObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false)
            .readValue(tuyaDeviceStatus, Array<Capability>::class.java)
            .filterNotNull()
            .also {
                if (it.isEmpty())
                    throw ApiError.CAPABILITY_PARSE_EXCEPTION.toException()
            }

    fun responseCapabilityCleaner(tuyaResponse: String) =
        tuyaResponse
            .replace("\\\"", "\"")
            .replace("\"{", "{")
            .replace("}\"", "}")
}