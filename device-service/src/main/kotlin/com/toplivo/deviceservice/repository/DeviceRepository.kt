package com.toplivo.deviceservice.repository

import com.toplivo.deviceservice.entity.DeviceEntity
import com.toplivo.deviceservice.dto.DeviceSimple
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DeviceRepository : JpaRepository<DeviceEntity, Int> {

    fun existsByIdAndOwnerId(id: Int, ownerId: Int): Boolean

    fun existsByHomeIdAndOwnerId(homeId: Int, ownerId: Int): Boolean

    fun getAllByHomeId(homeId: Int): List<DeviceSimple>

    fun getAllByRoomId(roomId: Int): List<DeviceSimple>

    fun findDeviceEntitiesByRoomId(roomId: Int): List<DeviceEntity>

    @Query("SELECT tuyaId from DeviceEntity where id = :id")
    fun getTuyaIdById(id: Int): String?

    fun deleteAllByHomeId(homeId: Int)
}