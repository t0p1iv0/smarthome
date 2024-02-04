package com.toplivo.userservice.service

import com.toplivo.userservice.entity.UserEntity
import com.toplivo.userservice.dto.request.*
import com.toplivo.userservice.dto.response.TokenPair
import com.toplivo.userservice.exception.ApiError
import com.toplivo.userservice.repository.UserRepository
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val refreshTokenService: RefreshTokenService,
    private val parseService: ParseService,
    private val eventService: EventService
) {

    private val log = KotlinLogging.logger {  }

    @Transactional
    fun register(request: UserRegisterRequest): TokenPair {
        if (request.password != request.confirmPassword)
            throw ApiError.PASSWORDS_DOES_NOT_MATCH.toException()

        if (userRepository.existsUserEntityByUsername(request.username))
            throw ApiError.USER_ALREADY_EXISTS.toException()

        val encodedPassword = passwordEncoder.encode(request.password)

        val user = request.toEntity(encodedPassword)

        userRepository.save(user)
            .also {
                log.info { "User ${it.username} with id: ${it.id} was successfully created" }
            }

        return refreshTokenService.generateAndSaveTokens(user.id)
    }


    fun auth(request: UserAuthRequest): TokenPair {
        val user = userRepository.findUserEntityByUsername(request.username)
            ?: throw ApiError.USER_NOT_FOUND.toException()

        if (!passwordEncoder.matches(request.password, user.password))
            throw ApiError.USER_NOT_FOUND.toException()

        log.info { "User ${request.username} was successfully authenticated" }

        return refreshTokenService.generateAndSaveTokens(user.id)
    }

    @Transactional
    fun signout(accessToken: String) {
        val accessId = parseService.parseAccessTokenId(accessToken)

        val username = parseService.parseUserId(accessToken)

        if (!refreshTokenService.validateRefreshToken(accessId))
            throw ApiError.INVALID_TOKEN.toException()

        refreshTokenService.deleteRefreshToken(accessId)

        log.info { "User $username was successfully sign out" }
    }

    @Transactional
    fun refresh(refreshRequest: RefreshRequest, accessToken: String): TokenPair {
        val accessTokenId = parseService.parseAccessTokenId(accessToken)

        if (!refreshTokenService.validateRefreshToken(accessTokenId))
            throw ApiError.INVALID_TOKEN.toException()

        refreshTokenService.deleteRefreshToken(accessTokenId)

        val userId = parseService.parseUserId(accessToken)

        return refreshTokenService.generateAndSaveTokens(userId)
    }

    @Transactional
    fun deleteUser(request: DeleteUserRequest, accessToken: String) {
        val userId = parseService.parseUserId(accessToken)

        val user = userRepository.findByIdOrNull(userId)
            ?: throw ApiError.USER_NOT_FOUND.toException()

        if (!passwordEncoder.matches(request.password, user.password))
            throw ApiError.USER_NOT_FOUND.toException()

        userRepository.deleteById(userId)

        eventService.saveEventMessage(userId)

        log.info { "User $userId was deleted" }
    }

    private fun UserRegisterRequest.toEntity(password: String) =
        UserEntity(
            name = name,
            username = username,
            password = password
        )
}