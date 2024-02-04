package com.toplivo.home_service.model

import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    property = "type",
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    visible = false
)

sealed class DomainEvent (
    val type: DomainEventType
) {
    abstract val value: Any
}

enum class DomainEventType {
    USER_DELETED, HOME_DELETED, ROOM_DELETED
}