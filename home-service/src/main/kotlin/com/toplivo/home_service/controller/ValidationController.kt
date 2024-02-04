package com.toplivo.home_service.controller

import com.toplivo.home_service.dto.request.ValidateRequest
import com.toplivo.home_service.service.ValidationService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("serv/verify")
class ValidationController(
    private val validation: ValidationService

) {
    @PostMapping("/home-owner")
    fun verifyOwner(
        @RequestHeader token: String,
        @Validated @RequestBody request: ValidateRequest
    ) = validation.validateOuterRequest(token, request)

}