import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.30"
	kotlin("plugin.spring") version "1.4.30"
}

group = "com.primer"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

val CONSTRAINTS_CONFIGURATION = "constraints"
val CONSTRAINTS_BLACKLIST = setOf("resolutionRules")

configurations {
	// https://github.com/gradle/gradle/issues/7576#issuecomment-434637595
	create(CONSTRAINTS_CONFIGURATION) {
		isCanBeConsumed = false
		isCanBeResolved = false
	}

	all {
		if (!CONSTRAINTS_BLACKLIST.contains(name) && isCanBeResolved) {
			extendsFrom(configurations.named(CONSTRAINTS_CONFIGURATION).get())
		}

		exclude(module = "spring-boot-starter-logging")
	}
}

repositories {
	mavenCentral()
}

object v {
	const val BRAINTREE = "3.6.0" // https://mvnrepository.com/artifact/com.braintreepayments.gateway/braintree-java
	const val JAVAX = "2.0.1.Final" // https://mvnrepository.com/artifact/javax.validation/validation-api
	const val LOG4J = "2.12.1" // https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-bom
	const val MOCKSERVER = "5.9.0" // https://mvnrepository.com/artifact/org.mock-server/mockserver-netty
	const val MOCKITO_KOTLIN = "2.2.0" // https://mvnrepository.com/artifact/com.nhaarman.mockitokotlin2/mockito-kotlin
	const val REST_ASSURED = "2.9.0" // https://mvnrepository.com/artifact/io.rest-assured/rest-assured/4.3.0
	const val TESTCONTAINERS = "1.14.2" // https://mvnrepository.com/artifact/org.testcontainers/testcontainers
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web") {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
		exclude(group = "tomcat-embed-el", module = "spring-boot-starter-tomcat")
	}
	implementation("org.springframework.boot:spring-boot-starter-undertow")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.braintreepayments.gateway:braintree-java:${v.BRAINTREE}")
	implementation("org.springframework.boot:spring-boot-starter-log4j2")
	implementation("javax.validation:validation-api:${v.JAVAX}")

	enforcedConstraints("org.apache.logging.log4j:log4j-bom:${v.LOG4J}")

	api("org.springframework.boot:spring-boot-starter-actuator")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}

	testImplementation("org.testcontainers:junit-jupiter:${v.TESTCONTAINERS}")
	testImplementation("org.testcontainers:testcontainers:${v.TESTCONTAINERS}")
	testImplementation("org.mock-server:mockserver-client-java:${v.MOCKSERVER}")

	testImplementation("com.jayway.restassured:rest-assured:${v.REST_ASSURED}")

	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
	testImplementation("org.springframework.security:spring-security-test")

	testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:${v.MOCKITO_KOTLIN}")
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testImplementation("org.junit.jupiter:junit-jupiter-params")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

fun DependencyHandlerScope.enforcedConstraints(bom: String) =
	add(CONSTRAINTS_CONFIGURATION, enforcedPlatform(bom))
