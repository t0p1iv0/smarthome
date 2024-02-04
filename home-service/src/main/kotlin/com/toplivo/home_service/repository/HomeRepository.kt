package com.toplivo.home_service.repository

import com.toplivo.home_service.entity.HomeEntity
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface HomeRepository : JpaRepository<HomeEntity, Int> {

    override fun findAll(): MutableList<HomeEntity>

    @EntityGraph(attributePaths = ["rooms"], type = EntityGraphType.FETCH)
    override fun findById(id: Int): Optional<HomeEntity>
    fun existsByIdAndOwnerId(homeId: Int, ownerId: Int): Boolean

    fun existsByOwnerId(ownerId: Int): Boolean

    fun findAllByOwnerId(ownerId: Int): List<HomeEntity>?

    @Query("SELECT id FROM HomeEntity WHERE ownerId = :ownerId")
    fun getHomeIdsByOwnerId(ownerId: Int): List<Int>?
}