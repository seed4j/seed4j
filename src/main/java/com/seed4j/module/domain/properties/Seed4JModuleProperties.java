package com.seed4j.module.domain.properties;

import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.javadependency.Version;
import com.seed4j.module.domain.nodejs.NodePackageManager;
import com.seed4j.shared.error.domain.Assert;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class Seed4JModuleProperties {

  public static final String BASE_PACKAGE_PARAMETER = "packageName";
  public static final String INDENTATION_PARAMETER = "indentSize";
  public static final String PROJECT_NAME_PARAMETER = "projectName";
  public static final String PROJECT_BASE_NAME_PARAMETER = "baseName";
  public static final String NODE_PACKAGE_MANAGER = "nodePackageManager";
  public static final String SERVER_PORT_PARAMETER = "serverPort";
  public static final String SPRING_CONFIGURATION_FORMAT = "springConfigurationFormat";
  public static final String JAVA_VERSION = "javaVersion";
  public static final String JAVA_BUILD_TOOL = "javaBuildTool";
  public static final String PROJECT_BUILD_DIRECTORY = "projectBuildDirectory";

  private final Seed4JProjectFolder projectFolder;
  private final boolean commitModule;
  private final Seed4JModuleParameters parameters;

  private final Indentation indentation;
  private final Seed4JBasePackage basePackage;
  private final Seed4JProjectName projectName;
  private final Seed4JProjectBaseName projectBaseName;
  private final NodePackageManager nodePackageManager;
  private final Seed4JServerPort serverPort;
  private final SpringConfigurationFormat springConfigurationFormat;
  private final Version javaVersion = new Version("25");

  public Seed4JModuleProperties(String projectFolder, boolean commitModule, Map<String, Object> parameters) {
    this.projectFolder = new Seed4JProjectFolder(projectFolder);
    this.commitModule = commitModule;
    this.parameters = new Seed4JModuleParameters(parameters);

    indentation = Indentation.from(this.parameters.getOrDefault(INDENTATION_PARAMETER, null, Integer.class));
    basePackage = new Seed4JBasePackage(this.parameters.getOrDefault(BASE_PACKAGE_PARAMETER, null, String.class));
    projectName = new Seed4JProjectName(this.parameters.getOrDefault(PROJECT_NAME_PARAMETER, null, String.class));
    projectBaseName = new Seed4JProjectBaseName(this.parameters.getOrDefault(PROJECT_BASE_NAME_PARAMETER, null, String.class));
    nodePackageManager = NodePackageManager.fromPropertyKey(
      this.parameters.getOrDefault(NODE_PACKAGE_MANAGER, NodePackageManager.NPM.propertyKey(), String.class)
    );
    serverPort = new Seed4JServerPort(this.parameters.getOrDefault(SERVER_PORT_PARAMETER, null, Integer.class));
    springConfigurationFormat = SpringConfigurationFormat.from(
      this.parameters.getOrDefault(SPRING_CONFIGURATION_FORMAT, SpringConfigurationFormat.YAML.get(), String.class)
    );
  }

  public Seed4JProjectFolder projectFolder() {
    return projectFolder;
  }

  public boolean commitNeeded() {
    return commitModule;
  }

  public Version javaVersion() {
    return javaVersion;
  }

  public String getString(String key) {
    return parameters.get(key, String.class);
  }

  public String getOrDefaultString(String key, String defaultValue) {
    Assert.notBlank("defaultValue", defaultValue);

    return parameters.getOrDefault(key, defaultValue, String.class, String::isBlank);
  }

  public Instant getInstantOrDefault(String key, Instant defaultValue) {
    String date = getOrDefaultString(key, defaultValue.toString());
    try {
      return Instant.parse(date);
    } catch (DateTimeParseException _) {
      throw InvalidPropertyTypeException.builder().key(key).expectedType(Instant.class).actualType(String.class);
    }
  }

  public boolean getBoolean(String key) {
    return parameters.get(key, Boolean.class);
  }

  public boolean getOrDefaultBoolean(String key, boolean defaultValue) {
    return parameters.getOrDefault(key, defaultValue, Boolean.class);
  }

  public int getInteger(String key) {
    return parameters.get(key, Integer.class);
  }

  public int getOrDefaultInteger(String key, int defaultValue) {
    return parameters.getOrDefault(key, defaultValue, Integer.class);
  }

  public Indentation indentation() {
    return indentation;
  }

  public String packagePath() {
    return basePackage.path();
  }

  public Seed4JBasePackage basePackage() {
    return basePackage;
  }

  public Seed4JProjectName projectName() {
    return projectName;
  }

  public Seed4JProjectBaseName projectBaseName() {
    return projectBaseName;
  }

  public NodePackageManager nodePackageManager() {
    return nodePackageManager;
  }

  public Seed4JServerPort serverPort() {
    return serverPort;
  }

  public SpringConfigurationFormat springConfigurationFormat() {
    return springConfigurationFormat;
  }

  public Map<String, Object> getParameters() {
    return parameters.get();
  }

  @Override
  public String toString() {
    return String.valueOf(projectName);
  }
}
