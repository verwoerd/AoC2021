plugins {
  id("com.pswidersk.python-plugin")
}

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
