package com.seed4j.generator.server.webjars.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class WebjarsModuleFactoryTest {

  private static final WebjarsModuleFactory factory = new WebjarsModuleFactory();

  @Test
  void shouldBuildWebjarsLocatorModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    SeedModule module = factory.buildWebjarsLocatorModule(properties);

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
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    SeedModule module = factory.buildWebjarsHtmxModule(properties);

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
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    SeedModule module = factory.buildWebjarsAlpineJSModule(properties);

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
