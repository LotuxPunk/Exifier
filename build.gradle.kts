import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
    application
}

group = "me.lotuxpunk"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven ("https://jitpack.io")
}

dependencies {
    implementation("com.github.ajalt.clikt:clikt:3.2.0")
    implementation("com.github.drewnoakes:metadata-extractor:2.16.0")

}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "MainKt"
}