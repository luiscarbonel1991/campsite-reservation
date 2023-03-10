plugins {
	java
	id("org.springframework.boot") version "3.0.1"
	id("io.spring.dependency-management") version "1.1.0"
	id("io.freefair.lombok") version "6.6.1"
	id("com.google.cloud.tools.jib") version "3.3.1"
}

group = "com.reservation"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

val springDocVersion = "2.0.2"
val commonsValidatorVersion = "1.7"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("commons-validator:commons-validator:$commonsValidatorVersion")
	implementation("org.hibernate.validator:hibernate-validator")

	// for lock registry
	implementation ("org.springframework.boot:spring-boot-starter-data-redis")
	implementation ("org.springframework.integration:spring-integration-redis")
	implementation ("io.lettuce:lettuce-core")

	// swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocVersion")

	// for mysql
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.mysql:mysql-connector-j")

	// lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// for testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.h2database:h2")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

jib {
	from {
		image = "openjdk:17-alpine"
	}
	to {
		image = project.name
	}
	container {
		jvmFlags = listOf("-Xms500m", "-Xmx500m")
	}
}
