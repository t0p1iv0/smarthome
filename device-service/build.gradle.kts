import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	kotlin("plugin.jpa") version "1.8.22"
}

group = "com.toplivo"
version = "v1.0.0"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
	maven { url = uri("https://maven-other.tuya.com/repository/maven-public/") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation("org.jetbrains.kotlin:kotlin-reflect")

	implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

	implementation("org.liquibase:liquibase-core")
	implementation("org.postgresql:postgresql")
	implementation("org.hibernate:hibernate-core:6.3.0.Final")

	implementation("com.tuya:tuya-spring-boot-starter:1.3.2")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}


allOpen {
	annotations("javax.persistence.Entity", "javax.persistence.MappedSuperclass", "javax.persistence.Embeddable")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

sourceSets.main {
	java.srcDirs("src/main/kotlin")
}

tasks.withType<BootRun> {
	jvmArgs(
		"--add-opens=java.base/java.lang=ALL-UNNAMED",
		"--add-opens=java.management/sun.management=ALL-UNNAMED",
		"--add-opens=java.base/sun.net=ALL-UNNAMED",
	)
}

sourceSets {
	main {
		java {
			srcDir("${buildDir.absoluteFile}/generated/src/main/kotlin")
		}
	}
}


tasks.withType<Test> {
	useJUnitPlatform()
}
