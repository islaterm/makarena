plugins {
  kotlin("jvm") version "1.5.31"
}

group = "cl.ravenhill"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  testImplementation("io.kotest:kotest-runner-junit5:4.6.3")
  implementation("io.kotest:kotest-assertions-core:4.6.3")
  testImplementation("io.kotest:kotest-property:4.6.3")
}

tasks.test {
  useJUnitPlatform()
}