package com.seed4j.generator.server.springboot.apidocumentation.springdocjwt.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringdocJwtModuleFactoryTest {

  private static final SpringdocJwtModuleFactory factory = new SpringdocJwtModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    SeedModule module = factory.buildModule(properties);

    assertThatModule(module).hasFiles(
      "src/main/java/com/seed4j/growth/wire/springdoc/infrastructure/primary/SpringdocJWTConfiguration.java"
    );
  }
}
