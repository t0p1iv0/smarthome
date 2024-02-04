package com.toplivo.userservice.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.toplivo.userservice.dto.response.TokenPair
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*


@Service
class JwtService (
    @Value("\${jwt.accessTokenTtl}")
    private val accessTokenTtl: Long,

    @Value("\${jwt.accessSecret}")
    private val accessSecret: String,
) {

    fun generateTokens(userId: Int): TokenPair {
        val accessToken = JWT.create()
            .withClaim("userId", userId)
            .withClaim("id", UUID.randomUUID().toString())
            .withExpiresAt(Instant.now().plusSeconds(accessTokenTtl))
            .sign(Algorithm.HMAC256(accessSecret))

        val refreshToken = UUID.randomUUID().toString()

        return TokenPair(
            accessToken,
            refreshToken,
            Instant.now().plusSeconds(accessTokenTtl).epochSecond
        )
    }
}