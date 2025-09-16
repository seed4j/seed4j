package com.seed4j.generator.server.springboot.apidocumentation.springdocjwt.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringdocJwtModuleFactoryTest {

  private static final SpringdocJwtModuleFactory factory = new SpringdocJwtModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModule(module).hasFiles(
      "src/main/java/com/seed4j/growth/wire/springdoc/infrastructure/primary/SpringdocJWTConfiguration.java"
    );
  }
}
