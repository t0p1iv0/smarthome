package com.toplivo.home_service.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Service

@Service
class ParseService {
    fun parseOwnerId(token: String) =
        jacksonObjectMapper()
            .readTree(token)
            .findValue("userId")
            .asInt()
}