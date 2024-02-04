package com.toplivo.home_service.entity

import com.toplivo.home_service.dto.Room
import jakarta.persistence.*

@Entity
@Table(name = "rooms")
class RoomEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = -1,
    val name: String,
    val homeId: Int
) {
    fun toDto() =
        Room(
            id = id,
            name = name,
        )
}