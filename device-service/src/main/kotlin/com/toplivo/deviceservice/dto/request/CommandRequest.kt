package com.toplivo.deviceservice.dto.request

import com.toplivo.deviceservice.dto.capabilities.Capability

class CommandRequest(
    val commands: List<Capability>
)