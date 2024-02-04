package com.toplivo.home_service.model

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName("HOME_DELETED")
class HomeDeletedEvent(
    override val value: Int
) : DomainEvent(type = DomainEventType.HOME_DELETED) {
}