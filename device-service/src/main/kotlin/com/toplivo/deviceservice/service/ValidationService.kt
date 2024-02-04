package com.toplivo.deviceservice.service

import com.toplivo.deviceservice.repository.DeviceRepository
import org.springframework.stereotype.Service

@Service
class ValidationService(
    private val deviceRepository: DeviceRepository
) {
    fun validateOwnerById(id: Int, ownerId: Int): Boolean =
        deviceRepository.existsByIdAndOwnerId(id, ownerId)

    fun validateOwnerByHomeId(ownerId: Int, homeId: Int): Boolean =
        deviceRepository.existsByHomeIdAndOwnerId(homeId, ownerId)
}