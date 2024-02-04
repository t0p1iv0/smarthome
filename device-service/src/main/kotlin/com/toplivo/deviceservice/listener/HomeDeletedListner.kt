package com.toplivo.deviceservice.listener

import com.toplivo.deviceservice.model.HomeDeletedEvent
import com.toplivo.deviceservice.service.DeviceService
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class HomeDeletedListner(
    private val deviceService: DeviceService
) {

    private val log = KotlinLogging.logger {  }
    @KafkaListener(
        topics = ["homes"],
        containerFactory = "kafkaDomainEventListenerContainerFactory"
    )
    fun listen(event: HomeDeletedEvent) {
        log.info { "Received event: $event" }

        deviceService.deleteDevices(event.value)
    }
}