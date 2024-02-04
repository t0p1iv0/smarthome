package com.toplivo.home_service.listener

import com.toplivo.home_service.model.UserDeletedEvent
import com.toplivo.home_service.service.HomeService
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserDeletedListener(
    private val homeService: HomeService
) {

    private val log = KotlinLogging.logger {  }
    @KafkaListener(
        topics = ["users"],
        containerFactory = "kafkaDomainEventListenerContainerFactory"
    )
    fun listen(event: UserDeletedEvent) {
        log.info { "Received event: $event" }

        homeService.deleteHomes(event.value)
    }
}