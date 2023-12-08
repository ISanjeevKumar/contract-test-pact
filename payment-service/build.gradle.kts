import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    id("au.com.dius.pact") version "4.1.4"
    id("org.springframework.boot") version "2.7.12"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "payment.service"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    //pact
    testImplementation("au.com.dius:pact-jvm-consumer-junit5:4.0.10")
    testImplementation("au.com.dius:pact-jvm-provider-junit5:4.0.10")

}

tasks.test {
    systemProperty("pact.verifier.publishResults", "true")
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

pact {
    publish {
        pactDirectory = "build/pacts"
        pactBrokerUrl = "http://localhost:9292/"
    }

}