package com.seed4j.generator.server.micronaut.common.domain;

import com.seed4j.module.domain.javaproperties.ApplicationPropertiesFile;

/**
 * Micronaut application properties files, to use with {@code applicationProperties(...)}: Micronaut only reads them at the
 * root of the classpath.
 */
public final class MicronautProperties {

  public static final ApplicationPropertiesFile MICRONAUT_MAIN = ApplicationPropertiesFile.named("application").in("src/main/resources/");
  public static final ApplicationPropertiesFile MICRONAUT_TEST = ApplicationPropertiesFile.named("application")
    .in("src/test/resources/")
    .profile("test");

  private MicronautProperties() {}
}
