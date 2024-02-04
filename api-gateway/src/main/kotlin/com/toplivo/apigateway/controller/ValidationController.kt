package com.toplivo.apigateway.controller

import com.toplivo.apigateway.client.ValidationClient
import com.toplivo.apigateway.client.model.ValidateRequestGen
import com.toplivo.apigateway.service.TokenService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("serv/verify")
class ValidationController(
    private val validationClient: ValidationClient,
    private val tokenService: TokenService
) {
    @PostMapping("/home-owner")
    fun verifyOwner(
        @Validated @RequestBody request: ValidateRequestGen
    ) = validationClient.verifyOwner(tokenService.getContext(), request)

}