package com.seed4j.generator.server.webjars.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class WebjarsModuleFactoryTest {

  private static final WebjarsModuleFactory factory = new WebjarsModuleFactory();

  @Test
  void shouldBuildWebjarsLocatorModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildWebjarsLocatorModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.webjars</groupId>
              <artifactId>webjars-locator-lite</artifactId>
            </dependency>
        """
      );
  }

  @Test
  void shouldBuildHtmxWebjarsModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildWebjarsHtmxModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.webjars.npm</groupId>
              <artifactId>htmx.org</artifactId>
              <version>${htmx-webjars.version}</version>
            </dependency>
        """
      );
  }

  @Test
  void shouldBuildAlpineJSWebjarsModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildWebjarsAlpineJSModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.webjars.npm</groupId>
              <artifactId>alpinejs</artifactId>
              <version>${alpinejs-webjars.version}</version>
            </dependency>
        """
      );
  }
}
