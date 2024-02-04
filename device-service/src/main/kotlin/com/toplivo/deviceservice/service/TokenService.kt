package com.toplivo.deviceservice.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Service

@Service
class TokenService {
    fun parseOwnerId(token: String) =
        jacksonObjectMapper()
            .readTree(token)
            .findValue("userId")
            .asInt()
}