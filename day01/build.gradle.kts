plugins {
  id("aoc.problem")
  id("com.pswidersk.python-plugin") version "1.3.0"
}
project.application.mainClass.set("MainKt")

 pythonPlugin {
   pythonVersion.set("3.9")
 }

tasks {
  register<com.pswidersk.gradle.python.VenvTask>("runPython") {
    group = "application"
    workingDir.set(projectDir.resolve("src/main/python"))
    args = listOf("solution.py")
  }
}
