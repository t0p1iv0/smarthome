import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("org.openapi.generator") version "7.1.0"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.jpa") version "1.8.22"
    kotlin("plugin.spring") version "1.6.21"
}

group = "com.toplivo"
version = "v1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.kafka:spring-kafka")

    implementation("com.auth0:java-jwt:4.4.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.2.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    implementation("net.logstash.logback:logstash-logback-encoder:7.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.register("generateGatewayClient", GenerateTask::class) {
    group = "openapi tools"
    input = project.file("$projectDir/src/main/resources/openapi/client/api-docs.yaml").path
    outputDir.set("$buildDir/generated")
    modelPackage.set("com.toplivo.apigateway.client.model")
    apiPackage.set("com.toplivo.apigateway.client.api")
    generatorName.set("kotlin")
    modelNameSuffix.set("Gen")
    templateDir.set("$projectDir/src/main/resources/openapi/templates")
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8-localdatetime",
            "useTags" to "true",
            "enumPropertyNaming" to "UPPERCASE",
            "serializationLibrary" to "jackson",
            "useCoroutines" to "true"
        )
    )
    additionalProperties.set(
        mapOf(
            "removeEnumValuePrefix" to "false"
        )
    )
}

tasks.withType<KotlinCompile> {
    dependsOn("generateGatewayClient")
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


sourceSets {
    main {
        java {
            srcDir("${buildDir.absoluteFile}/generated/src/main/kotlin")
        }
    }
}