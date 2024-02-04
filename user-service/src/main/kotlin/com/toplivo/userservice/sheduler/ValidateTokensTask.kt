package com.toplivo.userservice.sheduler

import com.toplivo.userservice.service.RefreshTokenService
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ValidateTokensTask (
    private val refreshTokenService: RefreshTokenService
) {

    private val logger = KotlinLogging.logger {  }

    @Scheduled(cron = "0 0 12 * * *")
    fun validateRefreshTokens() {
        logger.info { "Validation refresh tokens starts..." }

        refreshTokenService.deleteExpiredTokens()

        logger.info { "Refresh tokens validated" }
    }
}