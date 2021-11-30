val author: String by project

tasks {
  create("generateNextDay") {
    group = "aoc"
    actions = listOf(Action { DayGenerator.createNextDay(project.projectDir, author)})
  }
}
