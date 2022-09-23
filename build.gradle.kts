/*
 * "Makarena" (c) by R8V
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */
@file:Suppress("SpellCheckingInspection")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.7.0"
  id("com.diffplug.spotless") version "6.10.0"
}

group = "cl.ravenhill"
version = "0.2-ALPHA"

repositories {
  mavenCentral()
}

dependencies {
  testImplementation("io.kotest:kotest-runner-junit5:5.4.2")
  implementation("io.kotest:kotest-assertions-core:5.4.2")
  testImplementation("io.kotest:kotest-property:5.4.2")
  implementation(kotlin("stdlib-jdk8"))
  testImplementation("io.kotest:kotest-runner-junit5-jvm:4.6.0")
}

spotless {
  kotlin {
    diktat()//.configFile("diktat-analysis.yml")
  }
}

tasks.test {
  useJUnitPlatform()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
  jvmTarget = "1.8"
}