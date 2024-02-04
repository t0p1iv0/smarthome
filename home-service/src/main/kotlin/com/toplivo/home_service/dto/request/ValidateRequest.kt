package com.toplivo.home_service.dto.request

data class ValidateRequest(
    val homeId: Int,
    val roomId: Int?
) {
}