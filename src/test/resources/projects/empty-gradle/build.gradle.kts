plugins {
  java
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
  }
}

repositories {
  mavenCentral()
  // jhipster-needle-gradle-repository
}

group = "tech.jhipster.chips"
version = "0.0.1-SNAPSHOT"

ext {
  // jhipster-needle-gradle-property
}

dependencies {
  // jhipster-needle-gradle-add-dependency
  // jhipster-needle-gradle-add-dependency-test
}

tasks.test {
  filter {
    includeTestsMatching("*Test.*")
    excludeTestsMatching("*IntTest.*")
    excludeTestsMatching("*IT.*")
  }
  useJUnitPlatform()
}

val integrationTest = task<Test>("integrationTest") {
  description = "Runs integration tests."
  group = "verification"
  shouldRunAfter("test")
  filter {
    includeTestsMatching("*IntTest.*")
    includeTestsMatching("*IT.*")
    excludeTestsMatching("*Test.*")
  }
  useJUnitPlatform()
}
