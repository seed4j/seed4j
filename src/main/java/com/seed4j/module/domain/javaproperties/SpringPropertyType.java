package com.seed4j.module.domain.javaproperties;

public enum SpringPropertyType {
  MAIN_PROPERTIES("application"),
  MAIN_BOOTSTRAP_PROPERTIES("bootstrap"),
  TEST_PROPERTIES("application"),
  TEST_BOOTSTRAP_PROPERTIES("bootstrap");

  private final String filePrefix;

  SpringPropertyType(String filePrefix) {
    this.filePrefix = filePrefix;
  }

  public String filePrefix() {
    return filePrefix;
  }
}
