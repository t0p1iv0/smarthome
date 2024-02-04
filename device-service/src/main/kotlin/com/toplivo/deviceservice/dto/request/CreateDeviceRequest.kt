package com.toplivo.deviceservice.dto.request

data class CreateDeviceRequest (
    val tuyaDeviceId: String,
    val name: String?,
    val homeId: Int,
    val roomId: Int?
)