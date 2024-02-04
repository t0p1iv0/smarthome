package com.toplivo.home_service.service

import com.toplivo.home_service.dto.Room
import com.toplivo.home_service.dto.request.RoomRequest
import com.toplivo.home_service.entity.RoomEntity
import com.toplivo.home_service.exception.ApiError
import com.toplivo.home_service.repository.RoomRepository
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val validationService: ValidationService,
    private val parseService: ParseService,
    private val eventService: EventService
) {
    private val log = KotlinLogging.logger {  }

    @Transactional
    fun createRoom(token: String, homeId:Int, roomRequest: RoomRequest): Room {
        val ownerId = parseService.parseOwnerId(token)
        if (!validationService.validateByOwnerAndHomeId(ownerId, homeId))
            throw ApiError.HOME_NOT_FOUND.toException()

        return roomRequest.toEntity(homeId)
            .let { roomRepository.save(it) }
            .also {
                log.info {
                    "Room with id: ${it.id} was successfully created and assign to home: $homeId by user: $ownerId"
                }
            }
            .toDto()
    }

    @Transactional
    fun updateRoom(token: String, id: Int, roomRequest: RoomRequest): Room {
        val room = roomRepository.findByIdOrNull(id)
            ?: throw ApiError.ROOM_NOT_FOUND.toException()

        if (!validationService.validateByOwnerAndHomeId(parseService.parseOwnerId(token), room.homeId))
            throw ApiError.ROOM_NOT_FOUND.toException()

        return roomRepository.save(roomRequest.toEntity(room.homeId, id))
            .toDto()
    }
    @Transactional
    fun deleteRoom(token: String, id: Int) {
        val ownerId = parseService.parseOwnerId(token)
        if (!validationService.validateByOwnerAndRoomId(ownerId, id))
            throw ApiError.ROOM_NOT_FOUND.toException()

        roomRepository.deleteById(id)

        eventService.saveRoomEvent(id)

        log.info { "Home with id: $id was successfully delete by user with id: $ownerId" }
    }

    private fun RoomRequest.toEntity(homeId: Int = -1, id: Int = -1) =
        RoomEntity(
            id = id,
            name = name,
            homeId = homeId
        )
}