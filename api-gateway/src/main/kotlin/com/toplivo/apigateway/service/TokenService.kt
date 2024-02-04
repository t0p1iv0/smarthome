package com.toplivo.apigateway.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.toplivo.apigateway.exception.ApiError
import com.toplivo.apigateway.utils.TokenContextHolder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
    @Value("\${jwt.secret}")
    private val accessSecret: String
) {

    fun getContext() = TokenContextHolder.get() ?: throw ApiError.INVALID_TOKEN.toException()

    fun setContext(value: String) = TokenContextHolder.set(value)

    fun removeContext() = TokenContextHolder.remove()

    fun parseAndValidate(token: String) =
        Base64.getDecoder().decode(
        JWT.require(Algorithm.HMAC256(accessSecret))
            .build()
            .verify(token)
            .payload)
            .decodeToString()
}