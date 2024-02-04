package com.toplivo.apigateway.controller

import com.toplivo.apigateway.client.UserClient
import com.toplivo.apigateway.client.model.*
import com.toplivo.apigateway.service.TokenService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserController(
    private val userClient: UserClient,
    private val tokenService: TokenService
) {
    @PostMapping("/register")
    fun register(@RequestBody request: UserRegisterRequestGen) =
        userClient.register(request)

    @PostMapping("/auth")
    fun auth(@RequestBody request: UserAuthRequestGen) =
        userClient.auth(request)

    @SecurityRequirement(name = "X-Access-Token")
    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshRequestGen) =
        userClient.refresh(tokenService.getContext(), request)

    @SecurityRequirement(name = "X-Access-Token")
    @PostMapping("/signout")
    fun signout(@RequestBody request: AccessRequestGen) =
        userClient.signout(tokenService.getContext(), request)

    @SecurityRequirement(name = "X-Access-Token")
    @DeleteMapping("/{id}")
    fun delete(@RequestBody request: DeleteUserRequestGen) =
        userClient.deleteUser(tokenService.getContext(), request)
}