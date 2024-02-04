package com.toplivo.home_service.controller

import com.toplivo.home_service.dto.Room
import com.toplivo.home_service.dto.request.RoomRequest
import com.toplivo.home_service.service.RoomService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/rooms")
class RoomController(
    private val service: RoomService
) {
    @PostMapping
    fun createRoom(
        @RequestParam homeId: Int,
        @Validated @RequestBody request: RoomRequest,
        @RequestHeader token: String
    ): Room = service.createRoom(token, homeId, request)

    @PutMapping("/{roomId}")
    fun updateRoom(
        @PathVariable roomId: Int,
        @Validated @RequestBody request: RoomRequest,
        @RequestHeader token: String
    ): Room = service.updateRoom(token, roomId, request)

    @DeleteMapping("/{roomId}")
    fun deleteRoom(@PathVariable roomId: Int, @RequestHeader token: String) =
        service.deleteRoom(token, roomId)
}