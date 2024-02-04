package com.toplivo.apigateway.controller

import com.toplivo.apigateway.client.DeviceClient
import com.toplivo.apigateway.client.model.CommandRequestGen
import com.toplivo.apigateway.client.model.CreateDeviceRequestGen
import com.toplivo.apigateway.client.model.EditDeviceRequestGen
import com.toplivo.apigateway.service.TokenService
import com.toplivo.apigateway.service.ValidationService
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.web.bind.annotation.*
@RestController
@RequestMapping("/api/devices")
@SecurityScheme(name = "X-Access-Token", `in` = SecuritySchemeIn.HEADER, type = SecuritySchemeType.APIKEY)
@SecurityRequirement(name = "X-Access-Token")
class DeviceController(
    private val deviceClient: DeviceClient,
    private val tokenService: TokenService,
    private val validationService: ValidationService
) {
    @PostMapping
    fun createDevice(
        @RequestBody request: CreateDeviceRequestGen
    ) = validationService.validateHomeOwner(request)

    @GetMapping("/{id}")
    fun getDevice( @PathVariable id: Int) =
        deviceClient.getDevice(tokenService.getContext(), id)

    @PutMapping("/{id}")
    fun editDevice(
        @PathVariable id:Int,
        @RequestBody request: EditDeviceRequestGen
    ) = deviceClient.editDevice(tokenService.getContext(), id, request)

    @DeleteMapping("/{id}")
    fun deleteDevice(@PathVariable id: Int) =
        deviceClient.deleteDevice(tokenService.getContext(), id)

    @GetMapping
    fun getDevices(
        @RequestParam homeId: Int,
        @RequestParam roomId: Int?,
    ) = deviceClient.getDevices(tokenService.getContext(), homeId, roomId)

    @PostMapping("/{id}/control")
    fun sendCommand(
        @PathVariable id: Int,
        @RequestBody request: CommandRequestGen
    ) = deviceClient.sendCommand(tokenService.getContext(), id, request)
}