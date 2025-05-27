val micrometerTracingVersion: String by project
val openTelemetryExporterOtlpVersion: String by project


plugins {
	java
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.open.telemetry"
version = "0.0.1-SNAPSHOT"

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

	// tracing
	implementation("io.micrometer:micrometer-tracing:$micrometerTracingVersion")
	implementation("io.micrometer:micrometer-tracing-bridge-otel:$micrometerTracingVersion")

	implementation("io.opentelemetry:opentelemetry-exporter-otlp:$openTelemetryExporterOtlpVersion")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
