package tech.jhipster.lite.generator.server.springboot.mvc.security.kipe.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class KipeAuthorizationModuleFactoryTest {

  private static final KipeAuthorizationModuleFactory factory = new KipeAuthorizationModuleFactory();

  @Test
  void shouldBuildKipeExpressionModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFiles("documentation/kipe-authorization.md")
      .hasFiles("src/main/java/com/jhipster/test/shared/kipe/package-info.java")
      .hasFiles("src/main/java/com/jhipster/test/shared/kipe/application/MyappAuthorizations.java")
      .hasPrefixedFiles(
        "src/main/java/com/jhipster/test/shared/kipe/domain",
        "Accesses.java",
        "Action.java",
        "Resource.java",
        "RolesAccesses.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/jhipster/test/shared/kipe/application",
        "MyappAuthorizationsTest.java",
        "TestAuthentications.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/jhipster/test/shared/kipe/domain",
        "RolesAccessesFixture.java",
        "RolesAccessesTest.java",
        "ActionTest.java"
      );
  }
}
