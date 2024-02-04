package com.toplivo.home_service.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.toplivo.home_service.model.DomainEvent
import org.apache.kafka.common.serialization.Deserializer

class DomainEventDeserializer : Deserializer<DomainEvent> {

    private val mapper = jacksonObjectMapper()

    override fun deserialize(topic: String, data: ByteArray): DomainEvent {
        return mapper.readValue(data, DomainEvent::class.java)
    }
}