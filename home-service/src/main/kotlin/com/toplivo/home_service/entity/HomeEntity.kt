package com.toplivo.home_service.entity

import com.toplivo.home_service.dto.Home
import com.toplivo.home_service.dto.HomeSimple
import jakarta.persistence.*

@Entity
@Table(name = "homes")
class HomeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = -1,
    val ownerId: Int,
    val name: String,
    val address: String?,

    @OneToMany(mappedBy = "homeId", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var rooms: List<RoomEntity>? = null

) {
    fun toDto() =
        Home(
            id = id,
            name = name,
            address = address,
            rooms = rooms?.map { it.toDto() } ?: emptyList()
        )

    fun toSimpleDto() =
        HomeSimple(
            id = id,
            name = name
        )

    override fun toString(): String {
        return "HomeEntity(id=$id, name='$name', address=$address, rooms=$rooms)"
    }


}