plugins {
  java
  // jhipster-needle-gradle-plugins
}

val javaVersion by extra("21")
// jhipster-needle-gradle-properties

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

// jhipster-needle-gradle-plugins-configurations

repositories {
  mavenCentral()
  // jhipster-needle-gradle-repositories
}

group = "com.seed4j.growth"
version = "0.0.1-SNAPSHOT"

val profiles = (project.findProperty("profiles") as String? ?: "")
  .split(",")
  .map { it.trim() }
  .filter { it.isNotEmpty() }
// jhipster-needle-profile-activation

dependencies {
  // jhipster-needle-gradle-implementation-dependencies
  // jhipster-needle-gradle-compile-dependencies
  // jhipster-needle-gradle-runtime-dependencies
  // jhipster-needle-gradle-test-dependencies
}

tasks.test {
  filter {
    includeTestsMatching("**Test*")
    excludeTestsMatching("**IT*")
    excludeTestsMatching("**CucumberTest*")
  }
  useJUnitPlatform()
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
