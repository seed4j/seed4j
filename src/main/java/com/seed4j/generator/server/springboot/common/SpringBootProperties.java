package com.seed4j.generator.server.springboot.common;

import com.seed4j.module.domain.javaproperties.ApplicationPropertiesFile;

/**
 * Spring Boot application properties files, to use with {@code applicationProperties(...)}: files are searched in
 * {@code config/} first, then at the root of the resources folder, and created in {@code config/}.
 */
public final class SpringBootProperties {

  private static final String MAIN_CONFIG_FOLDER = "src/main/resources/config/";
  private static final String MAIN_RESOURCES_FOLDER = "src/main/resources/";
  private static final String TEST_CONFIG_FOLDER = "src/test/resources/config/";
  private static final String TEST_RESOURCES_FOLDER = "src/test/resources/";

  public static final ApplicationPropertiesFile SPRING_MAIN = ApplicationPropertiesFile.named("application").in(
    MAIN_CONFIG_FOLDER,
    MAIN_RESOURCES_FOLDER
  );
  public static final ApplicationPropertiesFile SPRING_LOCAL = SPRING_MAIN.profile("local");
  public static final ApplicationPropertiesFile SPRING_TEST = ApplicationPropertiesFile.named("application")
    .in(TEST_CONFIG_FOLDER, TEST_RESOURCES_FOLDER)
    .profile("test");
  public static final ApplicationPropertiesFile SPRING_MAIN_BOOTSTRAP = ApplicationPropertiesFile.named("bootstrap").in(
    MAIN_CONFIG_FOLDER,
    MAIN_RESOURCES_FOLDER
  );
  public static final ApplicationPropertiesFile SPRING_TEST_BOOTSTRAP = ApplicationPropertiesFile.named("bootstrap").in(
    TEST_CONFIG_FOLDER,
    TEST_RESOURCES_FOLDER
  );

  private SpringBootProperties() {}
}
