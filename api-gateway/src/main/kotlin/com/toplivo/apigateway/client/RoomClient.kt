package com.toplivo.apigateway.client

import com.toplivo.apigateway.client.api.RoomControllerApi
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class RoomClient : RoomControllerApi(
    basePath = "http://homeserver:8081",
    restTemplate = RestTemplate()
)