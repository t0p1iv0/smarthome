package com.toplivo.deviceservice.entity

import com.toplivo.deviceservice.dto.Device
import com.toplivo.deviceservice.dto.capabilities.Capability
import com.toplivo.deviceservice.enum.DeviceCategory
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes


@Entity
@Table(name = "devices")
class DeviceEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = -1,
    val tuyaId: String,
    val ownerId:Int,
    val name: String,
    val homeId: Int,
    val roomId: Int?,

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    val category: DeviceCategory
) {
    fun toDevice(capabilities: List<Capability>) =
        Device(
            id, name, category, capabilities
        )
}