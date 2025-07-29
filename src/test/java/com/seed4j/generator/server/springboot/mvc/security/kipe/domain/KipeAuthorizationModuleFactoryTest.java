package com.seed4j.generator.server.springboot.mvc.security.kipe.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class KipeAuthorizationModuleFactoryTest {

  private static final KipeAuthorizationModuleFactory factory = new KipeAuthorizationModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFiles("documentation/kipe-authorization.md")
      .hasFiles("src/main/java/tech/jhipster/jhlitest/shared/kipe/package-info.java")
      .hasFiles("src/main/java/tech/jhipster/jhlitest/shared/kipe/application/MyappAuthorizations.java")
      .hasPrefixedFiles(
        "src/main/java/tech/jhipster/jhlitest/shared/kipe/domain",
        "Accesses.java",
        "Action.java",
        "Resource.java",
        "RolesAccesses.java"
      )
      .hasPrefixedFiles(
        "src/test/java/tech/jhipster/jhlitest/shared/kipe/application",
        "MyappAuthorizationsTest.java",
        "TestAuthentications.java"
      )
      .hasPrefixedFiles(
        "src/test/java/tech/jhipster/jhlitest/shared/kipe/domain",
        "RolesAccessesFixture.java",
        "RolesAccessesTest.java",
        "ActionTest.java"
      );
  }
}
