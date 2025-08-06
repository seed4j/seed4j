plugins {
  java
  // seed4j-needle-gradle-plugins
}

val javaVersion by extra("21")
// seed4j-needle-gradle-properties

dependencies {
  // Access to the `libs` object doesn't work in precompiled script plugin
  // https://github.com/gradle/gradle/issues/15383
  // testImplementation(libs.spring.boot.devtools)

  // seed4j-needle-gradle-implementation-dependencies
  // seed4j-needle-gradle-compile-dependencies
  // seed4j-needle-gradle-runtime-dependencies
  // seed4j-needle-gradle-test-dependencies
}
