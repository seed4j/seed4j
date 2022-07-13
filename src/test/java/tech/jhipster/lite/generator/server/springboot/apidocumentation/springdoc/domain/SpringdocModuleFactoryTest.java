package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class SpringdocModuleFactoryTest {

  private static final SpringdocModuleFactory springdocModuleFactory = new SpringdocModuleFactory();

  @Test
  void shouldBuildModuleForMvc() {
    JHipsterModuleProperties moduleProperties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = springdocModuleFactory.buildModuleForMvc(moduleProperties);

    ModuleAsserter moduleAsserter = assertThatModuleWithFiles(module, pomFile())
      .hasFile("src/main/java/com/jhipster/test/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java")
      .notContaining("JWT")
      .and()
      .hasFile("pom.xml")
      .containing("<artifactId>springdoc-openapi-ui</artifactId>")
      .notContaining("<artifactId>springdoc-openapi-webflux-ui</artifactId>")
      .and();
    assertAddedProperties(moduleAsserter);
  }

  @Test
  void shouldBuildModuleForWebflux() {
    Map<String, Object> properties = new HashMap<>();
    properties.put(JHipsterModuleProperties.BASE_PACKAGE_PROPERTY, "com.jhipster.test");
    properties.put(JHipsterModuleProperties.PROJECT_BASE_NAME_PROPERTY, "myapp");
    JHipsterModuleProperties moduleProperties = new JHipsterModuleProperties(TestFileUtils.tmpDirForTest(), properties);

    JHipsterModule module = springdocModuleFactory.buildModuleForWebflux(moduleProperties);

    ModuleAsserter moduleAsserter = assertThatModuleWithFiles(module, pomFile())
      .hasFile("src/main/java/com/jhipster/test/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java")
      .notContaining("JWT")
      .and()
      .hasFile("pom.xml")
      .containing("<artifactId>springdoc-openapi-webflux-ui</artifactId>")
      .and();
    assertAddedProperties(moduleAsserter);
  }

  @Test
  void shouldBuildModuleWithSecurityJwtForMvc() {
    JHipsterModuleProperties moduleProperties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = springdocModuleFactory.buildModuleWithSecurityJwtForMvc(moduleProperties);

    ModuleAsserter moduleAsserter = assertThatModuleWithFiles(module, pomFile())
      .hasFile("src/main/java/com/jhipster/test/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java")
      .containing("JWT")
      .and()
      .hasFile("pom.xml")
      .containing("<artifactId>springdoc-openapi-ui</artifactId>")
      .notContaining("<artifactId>springdoc-openapi-webflux-ui</artifactId>")
      .and();

    assertAddedProperties(moduleAsserter);
  }

  @Test
  void shouldBuildModuleWithSecurityJwtForWebflux() {
    Map<String, Object> properties = new HashMap<>();
    properties.put(JHipsterModuleProperties.BASE_PACKAGE_PROPERTY, "com.jhipster.test");
    properties.put(JHipsterModuleProperties.PROJECT_BASE_NAME_PROPERTY, "myapp");
    JHipsterModuleProperties moduleProperties = new JHipsterModuleProperties(TestFileUtils.tmpDirForTest(), properties);

    JHipsterModule module = springdocModuleFactory.buildModuleWithSecurityJwtForWebflux(moduleProperties);

    ModuleAsserter moduleAsserter = assertThatModuleWithFiles(module, pomFile())
      .hasFile("src/main/java/com/jhipster/test/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java")
      .containing("JWT")
      .and()
      .hasFile("pom.xml")
      .containing("<artifactId>springdoc-openapi-webflux-ui</artifactId>")
      .and();

    assertAddedProperties(moduleAsserter);
  }

  private void assertAddedProperties(ModuleAsserter moduleFileAsserter) {
    moduleFileAsserter
      .hasFile("src/main/resources/config/application.properties")
      .containing("springdoc.swagger-ui.operationsSorter=alpha")
      .containing("springdoc.swagger-ui.tagsSorter=alpha")
      .containing("springdoc.swagger-ui.tryItOutEnabled=true");
  }
}
