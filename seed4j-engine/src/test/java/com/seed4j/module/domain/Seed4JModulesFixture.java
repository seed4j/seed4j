package com.seed4j.module.domain;

import com.seed4j.TestFileUtils;
import com.seed4j.module.domain.nodejs.NodePackageManager;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.properties.SpringConfigurationFormat;
import com.seed4j.shared.error.domain.Assert;
import java.util.HashMap;
import java.util.Map;

public final class Seed4JModulesFixture {

  private Seed4JModulesFixture() {}

  public static Seed4JModuleProperties testModuleProperties() {
    return new Seed4JModuleProperties(TestFileUtils.tmpDirForTest(), true, Map.of());
  }

  public static Seed4JModulePropertiesBuilder propertiesBuilder(String projectFolder) {
    return new Seed4JModulePropertiesBuilder(projectFolder);
  }

  public static final class Seed4JModulePropertiesBuilder {

    private boolean commitModules = false;
    private final String projectFolder;
    private final Map<String, Object> properties = new HashMap<>();

    private Seed4JModulePropertiesBuilder(String projectFolder) {
      this.projectFolder = projectFolder;
    }

    public Seed4JModulePropertiesBuilder commitModules() {
      commitModules = true;

      return this;
    }

    public Seed4JModulePropertiesBuilder basePackage(String basePackage) {
      properties.put("packageName", basePackage);

      return this;
    }

    public Seed4JModulePropertiesBuilder projectBaseName(String projectBaseName) {
      properties.put("baseName", projectBaseName);

      return this;
    }

    public Seed4JModulePropertiesBuilder projectName(String projectName) {
      properties.put("projectName", projectName);

      return this;
    }

    public Seed4JModulePropertiesBuilder nodePackageManager(NodePackageManager nodePackageManager) {
      Assert.notNull("nodePackageManager", nodePackageManager);
      properties.put("nodePackageManager", nodePackageManager.propertyKey());

      return this;
    }

    public Seed4JModulePropertiesBuilder springConfigurationFormat(SpringConfigurationFormat format) {
      Assert.notNull("format", format);
      properties.put("springConfigurationFormat", format.get());

      return this;
    }

    public Seed4JModulePropertiesBuilder put(String key, Object value) {
      properties.put(key, value);

      return this;
    }

    public Seed4JModuleProperties build() {
      return new Seed4JModuleProperties(projectFolder, commitModules, properties);
    }
  }
}
