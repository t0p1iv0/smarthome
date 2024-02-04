package com.toplivo.userservice.repository

import com.toplivo.userservice.entity.RefreshTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface JwtRepository : JpaRepository<RefreshTokenEntity, Int> {
    fun findRefreshTokenByAccessTokenId(accessToken: String): RefreshTokenEntity?

    fun deleteRefreshTokenByAccessTokenId(accessToken: String)

    @Modifying
    @Query("DELETE FROM RefreshTokenEntity WHERE expiresAt < :currentTime")
    fun deleteExpiredRefreshTokens(currentTime: Long)
}
