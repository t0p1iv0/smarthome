package com.toplivo.deviceservice.service

import com.toplivo.deviceservice.connector.DeviceConnector
import com.toplivo.deviceservice.converter.TuyaConverter
import com.toplivo.deviceservice.dto.capabilities.Capability
import com.toplivo.deviceservice.dto.tuya.TuyaSendCommandsRequest
import com.toplivo.deviceservice.exception.ApiError
import org.springframework.stereotype.Service

@Service
class TuyaService(
    private val deviceConnector: DeviceConnector,
    private val parseService: ParseService,
    private val tuyaConverter: TuyaConverter
) {
    fun getTuyaDevice(deviceId: String) =
        try {
            deviceConnector.getDeviceInfo(deviceId).toString()
        }
        catch (e: Exception) {
            throw ApiError.INVALID_TUYA_DEVICE_ID.toException()
        }

    fun getDeviceStatus(deviceId: String) =
        try {
            parseService.responseCapabilityCleaner(deviceConnector.getDeviceStatus(deviceId).toString())
        }
        catch (e: Exception) {
            throw ApiError.INVALID_TUYA_DEVICE_ID.toException()
        }

    fun sendCommands(deviceId: String, capabilities: List<Capability>) {

        deviceConnector.sendCommand(
            deviceId,
            TuyaSendCommandsRequest(tuyaConverter.convertToTuyaCapability(capabilities))
        )
    }
}