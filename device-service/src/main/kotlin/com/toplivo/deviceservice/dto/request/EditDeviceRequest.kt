package com.toplivo.deviceservice.dto.request

data class EditDeviceRequest (
    val name: String,
    val homeId: Int,
    val roomId: Int?
)