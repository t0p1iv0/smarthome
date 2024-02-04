package com.toplivo.apigateway.client

import com.toplivo.apigateway.client.api.DeviceControllerApi
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class DeviceClient : DeviceControllerApi(
    basePath = "http://deviceserver:8083",
    restTemplate = RestTemplate()
)