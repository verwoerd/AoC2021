plugins {
  id("aoc.kotlin")
  application
}

val year: String by project
val day : String = project.name.takeLast(2).dropWhile { it == '0' }
val session: String = extra["aoc.session"]?.toString() ?: ""
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
  withType<JavaExec>() {
    environment("AOC_YEAR" to year,"AOC_DAY" to day, "AOC_SESSION" to session )
  }
}
