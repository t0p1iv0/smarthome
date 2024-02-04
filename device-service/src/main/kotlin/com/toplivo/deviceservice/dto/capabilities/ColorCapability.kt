package com.toplivo.deviceservice.dto.capabilities

import com.toplivo.deviceservice.enum.CapabilityCode

data class ColorCapability(
    override val value: Map<String, Double>
) : Capability(code = CapabilityCode.COLOR)
