package com.toplivo.userservice.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ParseService (
    @Value("\${jwt.accessSecret}")
    private val accessSecret: String,
    ) {
    fun parseUserId(accessToken: String) =
        jacksonObjectMapper()
            .readTree(accessToken)
            .findValue("userId")
            .asInt()


    fun parseJwtAccessTokenId(accessToken: String) =
        JWT.require(Algorithm.HMAC256(accessSecret))
            .build()
            .verify(accessToken)
            .getClaim("id")
            .asString()

    fun parseAccessTokenId(accessToken: String) =
        jacksonObjectMapper()
            .readTree(accessToken)
            .findValue("id")
            .asText()
}