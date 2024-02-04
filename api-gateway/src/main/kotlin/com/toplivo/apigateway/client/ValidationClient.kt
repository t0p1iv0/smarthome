package com.toplivo.apigateway.client

import com.toplivo.apigateway.client.api.ValidationControllerApi
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class ValidationClient : ValidationControllerApi(
    basePath = "http://homeserver:8081",
    restTemplate = RestTemplate()
)