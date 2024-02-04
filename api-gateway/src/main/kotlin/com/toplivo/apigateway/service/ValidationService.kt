package com.toplivo.apigateway.service

import com.toplivo.apigateway.client.DeviceClient
import com.toplivo.apigateway.client.ValidationClient
import com.toplivo.apigateway.client.model.CreateDeviceRequestGen
import com.toplivo.apigateway.client.model.DeviceGen
import com.toplivo.apigateway.client.model.ValidateRequestGen
import com.toplivo.apigateway.exception.ApiError
import org.springframework.stereotype.Service

@Service
class ValidationService(
    private val validationClient: ValidationClient,
    private val deviceClient: DeviceClient,
    private val tokenService: TokenService
) {

    fun validateHomeOwner(request: CreateDeviceRequestGen): DeviceGen {
        val token = tokenService.getContext()
        val status = validationClient.verifyOwner(
            token,
            ValidateRequestGen(request.homeId, request.roomId)
        )

        if (!status.verified)
            throw ApiError.UNAUTHORIZED_REQUEST.toException()

        return deviceClient.createDevice(token, request)
    }
}