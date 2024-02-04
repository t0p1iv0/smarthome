package com.toplivo.home_service.dto.request

data class HomeRequest(
    val name: String,
    val address: String? = null
)