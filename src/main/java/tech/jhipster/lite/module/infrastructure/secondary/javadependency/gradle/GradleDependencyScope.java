package tech.jhipster.lite.module.infrastructure.secondary.javadependency.gradle;

enum GradleDependencyScope {
  API("api"),
  IMPLEMENTATION("implementation"),
  COMPILE_ONLY("compileOnly"),
  RUNTIME_ONLY("runtimeOnly"),
  TEST_IMPLEMENTATION("testImplementation"),
  TEST_COMPILE_ONLY("testCompileOnly"),
  TEST_RUNTIME_ONLY("testRuntimeOnly"),
  ;

  private final String command;

  GradleDependencyScope(String command) {
    this.command = command;
  }

  public String command() {
    return command;
  }
}
