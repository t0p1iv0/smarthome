package com.toplivo.home_service.repository

import com.toplivo.home_service.entity.RoomEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RoomRepository : JpaRepository<RoomEntity, Int> {

    @Query("SELECT homeId from RoomEntity where id = :id")
    fun getHomeIdById(id: Int): Int?

    fun existsByIdAndHomeId(id: Int, homeId: Int): Boolean
}