package com.toplivo.home_service.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.toplivo.home_service.entity.OutboxMessageEntity
import com.toplivo.home_service.model.DomainEvent
import com.toplivo.home_service.model.HomeDeletedEvent
import com.toplivo.home_service.model.RoomDeletedEvent
import com.toplivo.home_service.repository.OutboxMessageRepository
import org.springframework.stereotype.Service

@Service
class EventService(
    private val outboxMessageRepository: OutboxMessageRepository,
    private val mapper: ObjectMapper
) {
    fun saveHomeEvent(homeId: Int) {
        outboxMessageRepository.save(
            OutboxMessageEntity(
                topic = "homes",
                message = HomeDeletedEvent(homeId).toJsonString()
            )
        )
    }

    fun saveRoomEvent(roomId: Int) {
        outboxMessageRepository.save(
            OutboxMessageEntity(
                topic = "rooms",
                message = RoomDeletedEvent(roomId).toJsonString()
            )
        )
    }

    private fun DomainEvent.toJsonString() =
        mapper.writeValueAsString(this)
}