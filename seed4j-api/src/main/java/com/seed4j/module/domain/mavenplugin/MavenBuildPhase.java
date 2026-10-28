package com.seed4j.module.domain.mavenplugin;

public enum MavenBuildPhase {
  INITIALIZE("initialize"),
  VALIDATE("validate"),
  GENERATE_SOURCES("generate-sources"),
  PROCESS_SOURCES("process-sources"),
  GENERATE_RESOURCES("generate-resources"),
  PROCESS_RESOURCES("process-resources"),
  COMPILE("compile"),
  PROCESS_CLASSES("process-classes"),
  GENERATE_TEST_SOURCES("generate-test-sources"),
  PROCESS_TEST_SOURCES("process-test-sources"),
  GENERATE_TEST_RESOURCES("generate-test-resources"),
  PROCESS_TEST_RESOURCES("process-test-resources"),
  TEST_COMPILE("test-compile"),
  PROCESS_TEST_CLASSES("process-test-classes"),
  TEST("test"),
  PREPARE_PACKAGE("prepare-package"),
  PACKAGE("package"),
  PRE_INTEGRATION_TEST("pre-integration-test"),
  INTEGRATION_TEST("integration-test"),
  POST_INTEGRATION_TEST("post-integration-test"),
  VERIFY("verify"),
  INSTALL("install"),
  DEPLOY("deploy"),
  SITE("site");

  private final String mavenKey;

  MavenBuildPhase(String mavenKey) {
    this.mavenKey = mavenKey;
  }

  public String mavenKey() {
    return mavenKey;
  }
}
