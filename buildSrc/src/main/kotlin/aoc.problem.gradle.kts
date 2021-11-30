plugins {
  id("aoc.kotlin")
  application
}

val coroutineVersion: String by project
val implementation by configurations

dependencies {
  implementation(project(":shared"))
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
}

tasks {
  withType<Test>() {
    useJUnitPlatform()
  }
}
