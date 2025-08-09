package com.seed4j.generator.server.springboot.mvc.security.kipe.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class KipeAuthorizationModuleFactoryTest {

  private static final KipeAuthorizationModuleFactory factory = new KipeAuthorizationModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    SeedModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFiles("documentation/kipe-authorization.md")
      .hasFiles("src/main/java/com/seed4j/growth/shared/kipe/package-info.java")
      .hasFiles("src/main/java/com/seed4j/growth/shared/kipe/application/MyappAuthorizations.java")
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/shared/kipe/domain",
        "Accesses.java",
        "Action.java",
        "Resource.java",
        "RolesAccesses.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/shared/kipe/application",
        "MyappAuthorizationsTest.java",
        "TestAuthentications.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/shared/kipe/domain",
        "RolesAccessesFixture.java",
        "RolesAccessesTest.java",
        "ActionTest.java"
      );
  }
}
