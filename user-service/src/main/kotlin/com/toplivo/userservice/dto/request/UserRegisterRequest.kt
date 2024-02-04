package com.toplivo.userservice.dto.request

data class UserRegisterRequest (
    val name: String,
    val username: String,
    val password: String,
    val confirmPassword: String
)