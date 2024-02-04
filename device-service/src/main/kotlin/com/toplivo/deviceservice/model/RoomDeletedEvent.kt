package com.toplivo.deviceservice.model

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName("ROOM_DELETED")
class RoomDeletedEvent(
    override val value: Int
) : DomainEvent(type = DomainEventType.ROOM_DELETED)  {
}