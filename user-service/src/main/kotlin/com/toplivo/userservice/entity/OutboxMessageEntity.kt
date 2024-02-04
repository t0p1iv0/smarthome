package com.toplivo.userservice.entity

import jakarta.persistence.*

@Entity
@Table(name = "outbox_messages")
class OutboxMessageEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val topic: String,
    val message: String
)