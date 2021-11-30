plugins {
  kotlin("jvm")
}

repositories {
  mavenCentral()
}
val kotestVersion: String by project
val implementation by configurations

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(kotlin("reflect"))
  implementation("io.kotest:kotest-runner-junit5:$kotestVersion")
  implementation("io.kotest:kotest-assertions-core:$kotestVersion")
  implementation("io.kotest:kotest-property:$kotestVersion")
}

