val micrometerTracingVersion: String by project
val openTelemetryExporterOtlpVersion: String by project


plugins {
	java
	id("org.springframework.boot") version "3.5.0"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.open.telemetry"
version = "1.0.0-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-aop")

	// tracing
//	implementation("io.micrometer:micrometer-tracing:$micrometerTracingVersion")
	implementation("io.micrometer:micrometer-tracing-bridge-otel:$micrometerTracingVersion")

	implementation("io.opentelemetry:opentelemetry-exporter-otlp:$openTelemetryExporterOtlpVersion")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
