package com.toplivo.deviceservice.listener

import com.toplivo.deviceservice.model.RoomDeletedEvent
import com.toplivo.deviceservice.service.DeviceService
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class RoomDeletedListner(
    private val deviceService: DeviceService
) {

    private val log = KotlinLogging.logger {  }

    @KafkaListener(
        topics = ["rooms"],
        containerFactory = "kafkaDomainEventListenerContainerFactory"
    )
    fun listen(event: RoomDeletedEvent) {
        log.info { "Received event: $event" }

        deviceService.unboundRoom(event.value)
    }
}