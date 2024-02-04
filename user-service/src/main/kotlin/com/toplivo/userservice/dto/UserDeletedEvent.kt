package com.toplivo.userservice.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    property = "type",
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY
)
class UserDeletedEvent(
    val value: Int
) {
    val type = "USER_DELETED"
}