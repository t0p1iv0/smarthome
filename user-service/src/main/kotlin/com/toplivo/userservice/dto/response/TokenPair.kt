package com.toplivo.userservice.dto.response

data class TokenPair (
    val accessToken: String,
    val refreshToken: String,
    val ttl: Long
)