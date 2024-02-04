package com.toplivo.deviceservice

import com.tuya.connector.spring.annotations.ConnectorScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@ConnectorScan(basePackages = ["com.toplivo.deviceservice.connector"])
@SpringBootApplication
class DeviceServiceApplication

fun main(args: Array<String>) {
	runApplication<DeviceServiceApplication>(*args)
}
