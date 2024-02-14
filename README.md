# smarthome
Пет-проект для управления IoT устройствами. 

Проект совместим с девайсами под управлением TuyaOS https://developer.tuya.com/en/docs/iot/introduction-of-tuya?id=K914joffendwh

# Настройка
Для работы с Tuya API нужен API-ключ, доступный после регистрации:
https://developer.tuya.com/en/docs/IoT%20Device%20Management/iot-device-management-quick-start?id=Karozipkebot7

За интеграцию с Tuya API отвечает device-service. В [application.yaml](https://github.com/t0p1iv0/smarthome/blob/main/device-service/src/main/resources/application.yaml) нужно прописать полученные после регистрации ключи:
connector:
  region: EU
  ak: ${ACCESS_ID}
  sk: ${ACCESS_SECRET}

# Запуск приложения
Запуск производится через [docker-compose](https://github.com/t0p1iv0/smarthome/blob/main/docker/docker-compose.yaml)

После запуска приложения по адресу localhost:8080/api-docs.yaml будет доступен swagger с которым можно поиграться. 

 # Добавление устройств
 Для теста приложения есть возможность добавлять виртуальные девайсы. 
 1. На странице https://iot.tuya.com/cloud/ нужно создать проект ![image](https://github.com/t0p1iv0/smarthome/assets/145772216/f97f643c-e6c3-41da-9d95-06d156dce972)

 2. В проекте выбрать Devices -> Add Device -> Add Virtual Device![image](https://github.com/t0p1iv0/smarthome/assets/145772216/47c428bb-3e71-45f8-8ad6-f6c04d4bfd5a)

Для добавления реальных девайсов можно использовать мобильное приложение:
https://developer.tuya.com/en/docs/IoT%20Device%20Management/how-to-download-app
