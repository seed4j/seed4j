package tech.jhipster.lite.generator.project.domain;

import java.io.File;

public class Constants {

  private Constants() {}

  public static final String MAIN_JAVA = String.join(File.separator, "src", "main", "java");
  public static final String MAIN_WEBAPP = String.join(File.separator, "src", "main", "webapp");
  public static final String MAIN_RESOURCES = String.join(File.separator, "src", "main", "resources");
  public static final String TEMPLATE_FOLDER = "generator";
  public static final String DEPENDENCIES_FOLDER = "dependencies";
  public static final String CONFIG_FOLDER = "config";

  public static final String TEST_JAVA = String.join(File.separator, "src", "test", "java");
  public static final String TEST_RESOURCES = String.join(File.separator, "src", "test", "resources");
  public static final String TEST_TEMPLATE_RESOURCES = String.join(File.separator, TEST_RESOURCES, TEMPLATE_FOLDER);

  public static final String POM_XML = "pom.xml";
  public static final String LIQUIBASE_MASTER_XML = "master.xml";
  public static final String PACKAGE_JSON = "package.json";
}
