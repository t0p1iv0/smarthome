package com.toplivo.deviceservice.dto.capabilities

import com.toplivo.deviceservice.enum.CapabilityCode

data class SwitchLedCapability(
    override val value: Boolean,
) : Capability(code = CapabilityCode.SWITCH_LED)