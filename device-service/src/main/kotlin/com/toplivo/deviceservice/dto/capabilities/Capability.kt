package com.toplivo.deviceservice.dto.capabilities


import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.toplivo.deviceservice.enum.CapabilityCode
import com.toplivo.deviceservice.util.*

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "code",
)
@JsonSubTypes(
    JsonSubTypes.Type(
    names = [COLOR, TUYA_COLOR],
    value = ColorCapability::class
    ),
    JsonSubTypes.Type(
        names = [TEMPERATURE, TUYA_TEMPERATURE],
        value = TemperatureCapability::class
    ),
    JsonSubTypes.Type(
        names = [BRIGHTNESS, TUYA_BRIGHTNESS],
        value = BrightnessCapability::class
    ),
    JsonSubTypes.Type(
        names = [SWITCH_LED, TUYA_SWITCH_LED],
        value = SwitchLedCapability::class
    )
)
sealed class Capability(
    val code: CapabilityCode,
) {
    @get:JsonProperty("value")
    abstract val value: Any
}