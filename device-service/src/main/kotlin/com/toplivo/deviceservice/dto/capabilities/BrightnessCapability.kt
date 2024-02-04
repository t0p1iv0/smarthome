package com.toplivo.deviceservice.dto.capabilities

import com.toplivo.deviceservice.enum.CapabilityCode
import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class BrightnessCapability(
    @field:Min(value = 25)
    @field:Max(value = 255)
    override val value: Int,
) : Capability(code = CapabilityCode.BRIGHTNESS)