package com.toplivo.deviceservice.dto

import com.toplivo.deviceservice.dto.capabilities.Capability
import com.toplivo.deviceservice.enum.DeviceCategory

data class Device (
    val id: Int,
    val name: String,
    val category: DeviceCategory,
    val capabilities: List<Capability>
)