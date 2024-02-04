package com.toplivo.userservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "refresh_tokens")
class RefreshTokenEntity (
    @Id
    val id: String,
    val expiresAt: Long,
    val accessTokenId: String
)