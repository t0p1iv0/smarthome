package com.toplivo.deviceservice.connector;

import com.toplivo.deviceservice.dto.tuya.TuyaSendCommandsRequest;
import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.api.annotations.GET;
import com.tuya.connector.api.annotations.POST;
import com.tuya.connector.api.annotations.Path;

public interface DeviceConnector {

    @GET("/v2.0/cloud/thing/{deviceId}")
    Object getDeviceInfo(@Path("deviceId") String deviceId);

    @GET("/v1.0/iot-03/devices/{deviceId}/status")
    Object getDeviceStatus(@Path("deviceId") String deviceId);

    @POST("/v1.0/iot-03/devices/{deviceId}/commands")
    Object sendCommand(@Path("deviceId") String deviceId, @Body TuyaSendCommandsRequest body);
}
