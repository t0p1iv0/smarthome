package com.toplivo.deviceservice.dto

import com.toplivo.deviceservice.enum.DeviceCategory

data class DeviceSimple (
    val id: Int,
    val name: String,
    val category: DeviceCategory
)