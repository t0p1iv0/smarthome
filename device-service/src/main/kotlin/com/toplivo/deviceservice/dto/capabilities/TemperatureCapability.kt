package com.toplivo.deviceservice.dto.capabilities

import com.toplivo.deviceservice.enum.CapabilityCode
import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class TemperatureCapability(
    @field:Min(value = 0)
    @field:Max(value = 255)
    override val value: Int
) : Capability(code = CapabilityCode.TEMPERATURE)
