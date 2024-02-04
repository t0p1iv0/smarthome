package com.toplivo.userservice.sheduler

import com.toplivo.userservice.repository.OutboxMessageRepository
import jakarta.transaction.Transactional
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SendEventMessagesTask(
    private val outboxMessageRepository: OutboxMessageRepository,
    private val kafkaTemplate: KafkaTemplate<String, String>,
) {

    @Scheduled(fixedRate = 1000)
    @Transactional
    fun send() {
        val sentMessageIds = outboxMessageRepository.findAll()
            .map {
            kafkaTemplate.send(it.topic, it.message)
            it.id
        }

        if (sentMessageIds.isNotEmpty())
            outboxMessageRepository.deleteAllById(sentMessageIds)
    }
}