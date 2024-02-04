package com.toplivo.home_service.service

import com.toplivo.home_service.dto.ValidationStatus
import com.toplivo.home_service.dto.request.ValidateRequest
import com.toplivo.home_service.exception.ApiError
import com.toplivo.home_service.repository.HomeRepository
import com.toplivo.home_service.repository.RoomRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ValidationService(
    private val homeRepository: HomeRepository,
    private val roomRepository: RoomRepository,
    private val parseService: ParseService
) {

    private val log = KotlinLogging.logger {  }

    fun validateOuterRequest(token: String, request: ValidateRequest): ValidationStatus {

        log.warn { "RoomId = ${request.roomId}, HomeId = ${request.homeId}" }

        if (!validateByOwnerAndHomeId(
            ownerId = parseService.parseOwnerId(token),
            homeId = request.homeId))
            throw ApiError.HOME_NOT_FOUND.toException()
                .also { log.info { "Request validation failed for request with token: $token" } }

        if (request.roomId != 0)
            request.roomId
                ?.let {
                    if (!roomRepository.existsByIdAndHomeId(it, request.homeId))
                        throw ApiError.ROOM_NOT_FOUND.toException()
                            .also {
                                log.info { "Request validation failed for request with token: $token" }
                            }
                }

        return ValidationStatus(true)

    }
    fun validateByOwnerAndHomeId(ownerId: Int, homeId: Int): Boolean =
        homeRepository.existsByIdAndOwnerId(homeId = homeId, ownerId = ownerId)

    @Transactional
    fun validateByOwnerAndRoomId(ownerId: Int, roomId: Int): Boolean =
        roomRepository.getHomeIdById(roomId)
            ?.let {homeRepository.existsByIdAndOwnerId(homeId = it, ownerId = ownerId)}
            ?: false
}