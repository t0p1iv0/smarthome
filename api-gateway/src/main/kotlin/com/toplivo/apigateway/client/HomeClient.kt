package com.toplivo.apigateway.client

import com.toplivo.apigateway.client.api.HomeControllerApi
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class HomeClient : HomeControllerApi(
    basePath = "http://homeserver:8081",
    restTemplate = RestTemplate()
)