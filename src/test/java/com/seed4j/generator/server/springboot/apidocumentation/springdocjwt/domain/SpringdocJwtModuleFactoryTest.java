package com.seed4j.generator.server.springboot.apidocumentation.springdocjwt.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringdocJwtModuleFactoryTest {

  private static final SpringdocJwtModuleFactory factory = new SpringdocJwtModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module).hasFiles(
      "src/main/java/com/seed4j/growth/wire/springdoc/infrastructure/primary/SpringdocJWTConfiguration.java"
    );
  }
}
