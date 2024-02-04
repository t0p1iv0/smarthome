package com.toplivo.deviceservice.controller

import com.toplivo.deviceservice.dto.request.CommandRequest
import com.toplivo.deviceservice.dto.request.CreateDeviceRequest
import com.toplivo.deviceservice.dto.request.EditDeviceRequest
import com.toplivo.deviceservice.service.DeviceService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/devices")
class DeviceController (
    private val deviceService: DeviceService,
) {
    @PostMapping
    fun createDevice(@RequestHeader token: String, @RequestBody request: CreateDeviceRequest) =
        deviceService.createDevice(token, request)

    @GetMapping("/{id}")
    fun getDevice(@RequestHeader token: String, @PathVariable id: Int) =
        deviceService.getDevice(id, token)

    @PutMapping("/{id}")
    fun editDevice(@RequestHeader token: String, @PathVariable id:Int, @RequestBody request: EditDeviceRequest) =
        deviceService.editDevice( id, request, token)

    @DeleteMapping("/{id}")
    fun deleteDevice(@RequestHeader token: String, @PathVariable id: Int) =
        deviceService.deleteDevice(id, token)

    @GetMapping
    fun getDevices(@RequestHeader token: String, @RequestParam homeId: Int, @RequestParam roomId: Int?) =
        deviceService.getDevices(homeId, roomId, token)

    @PostMapping("/{id}/control")
    fun sendCommand(@RequestHeader token: String, @PathVariable id: Int, @RequestBody request: CommandRequest) =
        deviceService.sendCommand(id, request, token)
}