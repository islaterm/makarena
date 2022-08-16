@file:Suppress("SpellCheckingInspection")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotestVersion: String by project



plugins {
  kotlin("jvm") version "1.7.0"
}

group = "cl.ravenhill"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  testImplementation("io.kotest:kotest-runner-junit5:5.3.1")
  implementation("io.kotest:kotest-assertions-core:5.3.1")
  testImplementation("io.kotest:kotest-property:5.3.1")
}

tasks.test {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "13"
}