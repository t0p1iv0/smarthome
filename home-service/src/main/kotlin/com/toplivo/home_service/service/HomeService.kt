package com.toplivo.home_service.service

import com.toplivo.home_service.dto.Home
import com.toplivo.home_service.dto.HomeSimple
import com.toplivo.home_service.entity.HomeEntity
import com.toplivo.home_service.dto.request.HomeRequest
import com.toplivo.home_service.entity.RoomEntity
import com.toplivo.home_service.exception.ApiError
import com.toplivo.home_service.repository.HomeRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HomeService(
    private val homeRepository: HomeRepository,
    private val validationService: ValidationService,
    private val parseService: ParseService,
    private val eventService: EventService
) {
    private val log = KotlinLogging.logger {  }
    @Transactional
    fun createHome(token: String, homeRequest: HomeRequest): Home =
        homeRequest.toEntity(
            ownerId = parseService.parseOwnerId(token),
            address = homeRequest.address)
            .let { homeRepository.save(it) }
            .also {
                log.info {
                    "Home with id: ${it.id} was successfully created by user with id: ${it.ownerId}"
                }
            }
            .toDto()


    @Transactional(readOnly = true)
    fun getHomes(token: String): List<HomeSimple> =
        homeRepository.findAllByOwnerId(parseService.parseOwnerId(token))
            ?.map { it.toSimpleDto() }
            ?: throw ApiError.HOME_NOT_FOUND.toException()

    @Transactional(readOnly = true)
    fun getHome(token: String, id: Int): Home {
        if (!validationService.validateByOwnerAndHomeId(parseService.parseOwnerId(token), id))
            throw ApiError.HOME_NOT_FOUND.toException()

        return homeRepository.findById(id).get().toDto()
    }
    @Transactional
    fun updateHome(token: String, id: Int, homeRequest: HomeRequest): Home {
        if (!validationService.validateByOwnerAndHomeId(parseService.parseOwnerId(token), id))
            throw ApiError.HOME_NOT_FOUND.toException()

        val home = homeRepository.findById(id).get()

        return homeRepository.save(
            homeRequest.toEntity(
                id = id,
                address = homeRequest.address ?: home.address,
                rooms = home.rooms,
                ownerId = parseService.parseOwnerId(token)
            )
        ).toDto()
    }

    @Transactional
    fun deleteHome(token: String, id: Int) {
        val ownerId = parseService.parseOwnerId(token)

        if (!validationService.validateByOwnerAndHomeId(ownerId, id))
            throw ApiError.HOME_NOT_FOUND.toException()

        homeRepository.deleteById(id)

        eventService.saveHomeEvent(id)

        log.info { "Home with id: $id was successfully delete by user with id: $ownerId" }
    }

    @Transactional
    fun deleteHomes(ownerId: Int) {
        homeRepository.getHomeIdsByOwnerId(ownerId)
            ?.also { homeRepository.deleteAllById(it) }
            ?.map { eventService.saveHomeEvent(it) }
    }


    private fun HomeRequest.toEntity(
        id: Int = -1,
        ownerId: Int,
        address: String? = null,
        rooms: List<RoomEntity>? = null
    ) =
        HomeEntity(
            id = id,
            ownerId = ownerId,
            name = name,
            address = address,
            rooms = rooms
        )
}