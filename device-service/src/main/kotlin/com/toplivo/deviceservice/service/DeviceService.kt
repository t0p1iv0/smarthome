package com.toplivo.deviceservice.service

import com.toplivo.deviceservice.converter.dictionary.stringToCategory
import com.toplivo.deviceservice.dto.Device
import com.toplivo.deviceservice.entity.DeviceEntity
import com.toplivo.deviceservice.dto.DeviceSimple
import com.toplivo.deviceservice.dto.request.CreateDeviceRequest
import com.toplivo.deviceservice.dto.request.CommandRequest
import com.toplivo.deviceservice.dto.request.EditDeviceRequest
import com.toplivo.deviceservice.enum.DeviceCategory
import com.toplivo.deviceservice.exception.ApiError
import com.toplivo.deviceservice.repository.DeviceRepository
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeviceService (
    private val deviceRepository: DeviceRepository,
    private val tuyaService: TuyaService,
    private val validationService: ValidationService,
    private val parseService: ParseService,
    private val tokenService: TokenService
) {

    private val log = KotlinLogging.logger {  }

    @Transactional
    fun createDevice(token: String, request: CreateDeviceRequest): Device {

        val tuyaId = request.tuyaDeviceId

        val tuyaDevice = tuyaService.getTuyaDevice(tuyaId)
        val tuyaDeviceStatus = tuyaService.getDeviceStatus(tuyaId)

        val category = stringToCategory(parseService.parseDeviceCategory(tuyaDevice))
            ?: throw ApiError.DEVICE_PARSE_EXCEPTION.toException()

        val deviceEntity = deviceRepository.save(
            request.toEntity(
                tokenService.parseOwnerId(token),
                request.name?:parseService.parseDeviceName(tuyaDevice),
                category
            )
        ).also {
            log.info {
                "Device with tuyaId: ${it.tuyaId} was successfully created" +
                        " by user: ${it.ownerId} and assigned to home ${it.homeId}"
            }
        }


        return Device(
            id = deviceEntity.id,
            name = deviceEntity.name,
            category = category,
            capabilities = parseService.parseDeviceCapabilities(tuyaDeviceStatus)
        )
    }

    @Transactional
    fun editDevice(id: Int, request: EditDeviceRequest, token: String): Device {
        if (!validationService.validateOwnerById(
                id = id,
                ownerId = tokenService.parseOwnerId(token)
        ))
            throw ApiError.DEVICE_NOT_FOUND.toException()

        val device = deviceRepository.findByIdOrNull(id)
            ?: throw ApiError.DEVICE_NOT_FOUND.toException()

        val tuyaDeviceStatus = tuyaService.getDeviceStatus(device.tuyaId)

        val deviceEntity = deviceRepository.save(
            request.toEntity(
                device.ownerId,
                id,
                device.tuyaId,
                device.category
            )
        )

        return Device(
            id = deviceEntity.id,
            name = deviceEntity.name,
            category = deviceEntity.category,
            capabilities = parseService.parseDeviceCapabilities(tuyaDeviceStatus)
        )
    }

    @Transactional
    fun getDevice(id: Int, token: String): Device {
        if (!validationService.validateOwnerById(
                id = id,
                ownerId = tokenService.parseOwnerId(token)
        ))
            throw ApiError.DEVICE_NOT_FOUND.toException()

        val deviceEntity = deviceRepository.findByIdOrNull(id)
            ?: throw ApiError.DEVICE_NOT_FOUND.toException()

        val tuyaDeviceStatus = tuyaService.getDeviceStatus(deviceEntity.tuyaId)

        return deviceEntity.toDevice(
            parseService.parseDeviceCapabilities(tuyaDeviceStatus)
        )
    }

    @Transactional
    fun deleteDevice(id: Int, token: String) {
        val ownerId = tokenService.parseOwnerId(token)

        if (!validationService.validateOwnerById(ownerId = ownerId, id = id ))
            throw ApiError.DEVICE_NOT_FOUND.toException()

        deviceRepository.deleteById(id)

        log.info { "Device $id was deleted by user $ownerId" }
    }

    @Transactional
    fun getDevices(homeId: Int, roomId: Int?, token: String): List<DeviceSimple> {
        if (!validationService.validateOwnerByHomeId(
                ownerId = tokenService.parseOwnerId(token),
                homeId =  homeId
        ))
            throw ApiError.DEVICE_NOT_FOUND.toException()

        return roomId
            ?.let { deviceRepository.getAllByRoomId(it) }
            ?: deviceRepository.getAllByHomeId(homeId)
    }

    @Transactional
    fun sendCommand(id: Int, request: CommandRequest, token: String) {
        if (!validationService.validateOwnerById(id = id, ownerId = tokenService.parseOwnerId(token)))
            throw ApiError.DEVICE_NOT_FOUND.toException()

        val tuyaId = deviceRepository.getTuyaIdById(id)
            ?: throw ApiError.INVALID_TUYA_DEVICE_ID.toException()

        tuyaService.sendCommands(tuyaId, request.commands)
    }

    @Transactional
    fun deleteDevices(homeId: Int) {
        deviceRepository.deleteAllByHomeId(homeId)
    }

    @Transactional
    fun unboundRoom(roomId: Int) {

        deviceRepository.saveAll(
        deviceRepository.findDeviceEntitiesByRoomId(roomId)
            .map{
                    it.toEntityWithoutRoom()
            }
        )

    }

    private fun CreateDeviceRequest.toEntity(ownerId: Int, name: String, category: DeviceCategory) =
        DeviceEntity(
            tuyaId = tuyaDeviceId,
            name = name,
            homeId = homeId,
            roomId = roomId,
            ownerId = ownerId,
            category = category
        )

    private fun EditDeviceRequest.toEntity(ownerId: Int, id: Int, tuyaId: String, category: DeviceCategory) =
        DeviceEntity(
            id = id,
            tuyaId = tuyaId,
            name = name,
            homeId = homeId,
            roomId = roomId,
            ownerId = ownerId,
            category = category
        )

    private fun DeviceEntity.toEntityWithoutRoom() =
        DeviceEntity(
            id,
            tuyaId,
            ownerId,
            name,
            homeId,
            roomId = 0,
            category
        )
}