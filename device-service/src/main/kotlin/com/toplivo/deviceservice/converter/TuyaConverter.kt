package com.toplivo.deviceservice.converter

import com.toplivo.deviceservice.converter.dictionary.toTuyaCode
import com.toplivo.deviceservice.dto.capabilities.Capability
import com.toplivo.deviceservice.dto.tuya.TuyaCapability
import com.toplivo.deviceservice.exception.ApiError
import org.springframework.stereotype.Component

@Component
class TuyaConverter {

    fun convertToTuyaCapability(list: List<Capability>) =
        list.map {
            TuyaCapability(
                it.code.toTuyaCode()
                    ?:throw ApiError.CAPABILITY_PARSE_EXCEPTION.toException(),
                it.value
            )
        }
}