plugins {
  java
  alias(libs.plugins.jib)
  alias(libs.plugins.protobuf)
  alias(libs.plugins.modernizer)
  // jhipster-needle-gradle-plugin
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

// jhipster-needle-gradle-project-extension-plugin-configuration

repositories {
  mavenCentral()
  // jhipster-needle-gradle-repositories
}

group = "com.seed4j"
version = "0.0.1-SNAPSHOT"

// jhipster-needle-profile-activation

dependencies {
  // jhipster-needle-gradle-implementation-dependencies
  // jhipster-needle-gradle-compile-dependencies
  // jhipster-needle-gradle-runtime-dependencies
  // jhipster-needle-gradle-test-dependencies
}
