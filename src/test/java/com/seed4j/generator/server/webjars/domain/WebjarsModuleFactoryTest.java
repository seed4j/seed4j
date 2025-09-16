package com.seed4j.generator.server.webjars.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class WebjarsModuleFactoryTest {

  private static final WebjarsModuleFactory factory = new WebjarsModuleFactory();

  @Test
  void shouldBuildWebjarsLocatorModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildWebjarsLocatorModule(properties);

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
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildWebjarsHtmxModule(properties);

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
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildWebjarsAlpineJSModule(properties);

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
