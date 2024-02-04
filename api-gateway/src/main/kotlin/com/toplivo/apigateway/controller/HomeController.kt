package com.toplivo.apigateway.controller

import com.toplivo.apigateway.client.HomeClient
import com.toplivo.apigateway.client.model.HomeRequestGen
import com.toplivo.apigateway.service.TokenService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/homes")
@SecurityRequirement(name = "X-Access-Token")
class HomeController(
    private val homeClient: HomeClient,
    private val tokenService: TokenService
) {
    @PostMapping
    fun createHome(@RequestBody request: HomeRequestGen) =
        homeClient.createHome(tokenService.getContext(), request)

    @GetMapping
    fun getHomes() =
        homeClient.getHomes(tokenService.getContext())

    @GetMapping("/{homeId}")
    fun getHomeById(@PathVariable homeId: Int,
    ) = homeClient.getHomeById(tokenService.getContext(), homeId)

    @PutMapping("/{homeId}")
    fun updateHome(
        @PathVariable homeId: Int,
        @Validated @RequestBody request: HomeRequestGen,
    ) = homeClient.updateHome(tokenService.getContext(), homeId, request)

    @DeleteMapping("/{homeId}")
    fun deleteHomeById(@PathVariable homeId: Int,
    ) = homeClient.deleteHomeById(tokenService.getContext(), homeId)
}