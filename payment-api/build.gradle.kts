import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    id("au.com.dius.pact") version "4.1.4"
    id("org.springframework.boot") version "2.7.12"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.6")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.3.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test:2.8.3")
    testImplementation("io.projectreactor:reactor-test")
    //pact
    testImplementation("au.com.dius:pact-jvm-consumer-junit5:4.0.10")
    testImplementation("au.com.dius:pact-jvm-provider-junit5:4.0.10")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
