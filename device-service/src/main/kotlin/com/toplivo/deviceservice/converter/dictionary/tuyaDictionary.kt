package com.toplivo.deviceservice.converter.dictionary

import com.google.common.collect.EnumHashBiMap
import com.toplivo.deviceservice.enum.CapabilityCode
import com.toplivo.deviceservice.enum.DeviceCategory
import com.toplivo.deviceservice.util.TUYA_BRIGHTNESS
import com.toplivo.deviceservice.util.TUYA_COLOR
import com.toplivo.deviceservice.util.TUYA_SWITCH_LED
import com.toplivo.deviceservice.util.TUYA_TEMPERATURE

private val capabilityDictionary: EnumHashBiMap<CapabilityCode, String> =
    EnumHashBiMap.create(
        mapOf(
            CapabilityCode.SWITCH_LED to TUYA_SWITCH_LED,
            CapabilityCode.TEMPERATURE to TUYA_TEMPERATURE,
            CapabilityCode.COLOR to TUYA_COLOR,
            CapabilityCode.BRIGHTNESS to TUYA_BRIGHTNESS
        )
    )

private val categoryDictionary: EnumHashBiMap<DeviceCategory, String> =
    EnumHashBiMap.create(
        mapOf(
            DeviceCategory.LIGHT to "dj"
        )
    )


fun CapabilityCode.toTuyaCode(): String? =
    capabilityDictionary[this]

fun stringToCategory(code: String): DeviceCategory? =
    categoryDictionary.inverse()[code]