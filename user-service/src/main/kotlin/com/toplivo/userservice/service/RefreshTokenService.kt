package com.toplivo.userservice.service

import com.toplivo.userservice.entity.RefreshTokenEntity
import com.toplivo.userservice.repository.JwtRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class RefreshTokenService (
    @Value("\${jwt.refreshTokenTtl}")
    private val refreshTokenTtl: Long,

    private val jwtRepository: JwtRepository,
    private val parseService: ParseService,
    private val jwtService: JwtService
) {
    fun validateRefreshToken(accessTokenId: String): Boolean {
        val refreshToken = jwtRepository.findRefreshTokenByAccessTokenId(accessTokenId)
            ?: return false

        if(refreshToken.expiresAt < Instant.now().epochSecond) {
            deleteRefreshToken(accessTokenId)
            return false
        }
        return true
    }

    fun deleteRefreshToken(accessTokenId: String) {
        jwtRepository.deleteRefreshTokenByAccessTokenId(accessTokenId)
    }

    fun storeRefreshToken(accessToken: String, refreshToken: String) =
        jwtRepository.save(
            RefreshTokenEntity(
                id = refreshToken,
                expiresAt = Instant.now().plusSeconds(refreshTokenTtl).epochSecond,
                accessTokenId = parseService.parseJwtAccessTokenId(accessToken)
            )
        )

    fun generateAndSaveTokens(userId: Int) =
        jwtService.generateTokens(userId)
            .also { storeRefreshToken(it.accessToken, it.refreshToken) }


    @Transactional
    fun deleteExpiredTokens() {
        jwtRepository.deleteExpiredRefreshTokens(Instant.now().epochSecond)
    }
}