package com.toplivo.apigateway.controller

import com.toplivo.apigateway.client.RoomClient
import com.toplivo.apigateway.client.model.RoomRequestGen
import com.toplivo.apigateway.service.TokenService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/rooms")
@SecurityRequirement(name = "X-Access-Token")
class RoomController(
    private val roomClient: RoomClient,
    private val tokenService: TokenService
) {
    @PostMapping
    fun createRoom(
        @RequestParam homeId: Int,
        @Validated @RequestBody request: RoomRequestGen
    ) = roomClient.createRoom(tokenService.getContext().also { println(it) }, homeId, request)

    @PutMapping("/{roomId}")
    fun updateRoom(
        @PathVariable roomId: Int,
        @Validated @RequestBody request: RoomRequestGen
    ) = roomClient.updateRoom(tokenService.getContext(), roomId, request)

    @DeleteMapping("/{roomId}")
    fun deleteRoom(@PathVariable roomId: Int) =
        roomClient.deleteRoom(tokenService.getContext(), roomId)
}