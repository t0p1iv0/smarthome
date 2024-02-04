package com.toplivo.deviceservice.model

import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    property = "type",
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY
)
sealed class DomainEvent (
    val type: DomainEventType
) {
    abstract val value: Any
}

enum class DomainEventType {
    HOME_DELETED, ROOM_DELETED
}