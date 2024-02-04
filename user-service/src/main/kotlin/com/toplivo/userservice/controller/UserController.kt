package com.toplivo.userservice.controller

import com.toplivo.userservice.dto.request.*
import com.toplivo.userservice.service.AuthService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(("/api"))
class UserController (
    private val authService: AuthService,

) {
    @PostMapping("/register")
    fun register(@RequestBody request: UserRegisterRequest) =
        authService.register(request)

    @PostMapping("/auth")
    fun auth(@RequestBody request: UserAuthRequest) =
        authService.auth(request)

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshRequest, @RequestHeader token: String) =
        authService.refresh(request, token)

    @PostMapping("/signout")
    fun signout(@RequestHeader token: String) =
        authService.signout(token)

    @DeleteMapping("/account")
    fun deleteUser(@RequestBody request: DeleteUserRequest, @RequestHeader token: String) =
        authService.deleteUser(request, token)
}