package com.toplivo.home_service.model

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName("USER_DELETED")
class UserDeletedEvent(
    override val value: Int
) : DomainEvent(type = DomainEventType.USER_DELETED) {

}