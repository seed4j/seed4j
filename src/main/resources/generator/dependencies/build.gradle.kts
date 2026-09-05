plugins {
  java
  alias(libs.plugins.jib)
  alias(libs.plugins.protobuf)
  alias(libs.plugins.modernizer)
  // seed4j-needle-gradle-plugin
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(25)
  }
}

// seed4j-needle-gradle-project-extension-plugin-configuration

repositories {
  mavenCentral()
  // seed4j-needle-gradle-repositories
}

group = "com.seed4j"
version = "0.0.1-SNAPSHOT"

// seed4j-needle-profile-activation

dependencies {
  // seed4j-needle-gradle-implementation-dependencies
  // seed4j-needle-gradle-compile-dependencies
  // seed4j-needle-gradle-annotation-processors
  // seed4j-needle-gradle-runtime-dependencies
  // seed4j-needle-gradle-test-dependencies
}
