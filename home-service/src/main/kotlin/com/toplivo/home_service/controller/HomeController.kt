package com.toplivo.home_service.controller

import com.toplivo.home_service.dto.Home
import com.toplivo.home_service.dto.HomeSimple
import com.toplivo.home_service.dto.request.HomeRequest
import com.toplivo.home_service.service.HomeService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/homes")
class HomeController(
    private val service: HomeService,
) {

    @PostMapping
    fun createHome(@Validated @RequestBody request: HomeRequest, @RequestHeader token: String): Home = service.createHome(token, request)

    @GetMapping
    fun getHomes(@RequestHeader token: String): List<HomeSimple> = service.getHomes(token)

    @GetMapping("/{homeId}")
    fun getHomeById(@PathVariable homeId: Int, @RequestHeader token: String): Home = service.getHome(token, homeId)

    @PutMapping("/{homeId}")
    fun updateHome(
        @PathVariable homeId: Int,
        @Validated @RequestBody request: HomeRequest,
        @RequestHeader token: String
    ): Home = service.updateHome(token, homeId, request)

    @DeleteMapping("/{homeId}")
    fun deleteHomeById(@PathVariable homeId: Int, @RequestHeader token: String) = service.deleteHome(token, homeId)
}