//// JHipsterModulesFixture.java
//
//package tech.jhipster.lite.module.domain;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import tech.jhipster.lite.TestFileUtils;
//import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public final class JHipsterModulesFixture {
//
//  private static final Logger log = LoggerFactory.getLogger(JHipsterModulesFixture.class);
//
//  private JHipsterModulesFixture() {
//  }
//
//  public static JHipsterModule module() {
//    // Module creation logic
//  }
//
//  // Other methods...
//
//  public static JHipsterModulePropertiesBuilder propertiesBuilder(String projectFolder) {
//    return new JHipsterModulePropertiesBuilder(projectFolder);
//  }
//
//  // Nested class for building module properties
//  public static class JHipsterModulePropertiesBuilder {
//    private boolean commitModules = false;
//    private final String projectFolder;
//    private final Map<String, Object> properties;
//
//    public JHipsterModulePropertiesBuilder(String projectFolder) {
//      this.projectFolder = projectFolder;
//      this.properties = new HashMap<>();
//    }
//
//    public JHipsterModulePropertiesBuilder commitModules() {
//      commitModules = true;
//      return this;
//    }
//
//    public JHipsterModulePropertiesBuilder basePackage(String basePackage) {
//      properties.put("packageName", basePackage);
//      return this;
//    }
//
//    public JHipsterModulePropertiesBuilder projectBaseName(String projectBaseName) {
//      properties.put("baseName", projectBaseName);
//      return this;
//    }
//
//    public JHipsterModulePropertiesBuilder projectName(String projectName) {
//      properties.put("projectName", projectName);
//      return this;
//    }
//
//    public JHipsterModulePropertiesBuilder put(String key, Object value) {
//      properties.put(key, value);
//      return this;
//    }
//
//    public JHipsterModuleProperties build() {
//      return new JHipsterModuleProperties(projectFolder, commitModules, properties);
//    }
//  }
//}
