package com.toplivo.apigateway.client

import org.springframework.stereotype.Component
import com.toplivo.apigateway.client.api.UserControllerApi
import org.springframework.web.client.RestTemplate

@Component
class UserClient : UserControllerApi(
    basePath = "http://userserver:8082",
    restTemplate = RestTemplate()
)