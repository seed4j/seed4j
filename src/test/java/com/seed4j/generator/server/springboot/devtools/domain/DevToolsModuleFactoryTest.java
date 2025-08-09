package com.seed4j.generator.server.springboot.devtools.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class DevToolsModuleFactoryTest {

  private static final DevToolsModuleFactory factory = new DevToolsModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    SeedModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasPrefixedFiles("documentation", "dev-tools.md")
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-devtools</artifactId>
              <scope>runtime</scope>
              <optional>true</optional>
            </dependency>
        """
      )
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        spring:
          devtools:
            livereload:
              enabled: false
            restart:
              enabled: false
        """
      )
      .and()
      .hasFile("src/main/resources/config/application-local.yml")
      .containing(
        """
        spring:
          devtools:
            livereload:
              enabled: true
            restart:
              enabled: true
        """
      );
  }
}
