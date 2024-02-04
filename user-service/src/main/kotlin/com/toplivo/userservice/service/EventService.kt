package com.toplivo.userservice.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.toplivo.userservice.dto.UserDeletedEvent
import com.toplivo.userservice.entity.OutboxMessageEntity
import com.toplivo.userservice.repository.OutboxMessageRepository
import org.springframework.stereotype.Service

@Service
class EventService(
    private val outboxMessageRepository: OutboxMessageRepository,
    private val mapper: ObjectMapper
) {

    fun saveEventMessage(userId: Int) {
        outboxMessageRepository.save(
            OutboxMessageEntity(
                topic = "users",
                message = UserDeletedEvent(userId).toJsonString()
            )
        )
    }

    private fun UserDeletedEvent.toJsonString() =
        mapper.writeValueAsString(this)
}