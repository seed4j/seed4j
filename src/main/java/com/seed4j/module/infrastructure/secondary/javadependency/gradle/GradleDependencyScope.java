package com.seed4j.module.infrastructure.secondary.javadependency.gradle;

enum GradleDependencyScope {
  IMPLEMENTATION("implementation"),
  COMPILE_ONLY("compileOnly"),
  RUNTIME_ONLY("runtimeOnly"),
  TEST_IMPLEMENTATION("testImplementation");

  private final String command;

  GradleDependencyScope(String command) {
    this.command = command;
  }

  public String command() {
    return command;
  }
}
