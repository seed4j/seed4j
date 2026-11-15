// seed4j-needle-gradle-imports

plugins {
  java
  // seed4j-needle-gradle-plugins
}

// seed4j-needle-gradle-properties

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(25)
  }
}

// seed4j-needle-gradle-plugins-configurations

repositories {
  mavenCentral()
  // seed4j-needle-gradle-repositories
}

group = "com.seed4j.growth"
version = "0.0.1-SNAPSHOT"

val profiles = (project.findProperty("profiles") as String? ?: "")
  .split(",")
  .map { it.trim() }
  .filter { it.isNotEmpty() }
// seed4j-needle-profile-activation

dependencies {
  // seed4j-needle-gradle-implementation-dependencies
  // seed4j-needle-gradle-compile-dependencies
  // seed4j-needle-gradle-annotation-processors
  // seed4j-needle-gradle-runtime-dependencies
  // seed4j-needle-gradle-test-dependencies
}

// seed4j-needle-gradle-free-configuration-blocks

tasks.test {
  filter {
    includeTestsMatching("**Test*")
    excludeTestsMatching("**IT*")
    excludeTestsMatching("**CucumberTest*")
  }
  useJUnitPlatform()
  // seed4j-needle-gradle-tasks-test
}

val integrationTest = task<Test>("integrationTest") {
  description = "Runs integration tests."
  group = "verification"
  shouldRunAfter("test")
  filter {
    includeTestsMatching("**IT*")
    includeTestsMatching("**CucumberTest*")
  }
  useJUnitPlatform()
}
