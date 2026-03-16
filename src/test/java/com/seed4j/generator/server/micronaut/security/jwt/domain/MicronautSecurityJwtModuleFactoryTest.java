package com.seed4j.generator.server.micronaut.security.jwt.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class MicronautSecurityJwtModuleFactoryTest {

  private final MicronautSecurityJwtModuleFactory factory = new MicronautSecurityJwtModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("src/main/java/com/seed4j/growth/account/infrastructure/primary/SecurityConfiguration.java")
      .containing("package com.seed4j.growth.account.infrastructure.primary;")
      .and()
      .hasFile("pom.xml")
      .containing(
        """
              <groupId>io.micronaut.security</groupId>
              <artifactId>micronaut-security-jwt</artifactId>\
        """
      )
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing("micronaut:")
      .containing("security:")
      .containing("authentication: bearer")
      .containing("jwt:");
  }
}
