package tech.jhipster.lite.generator.server.springboot.mvc.security.kipe.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class KipeExpressionModuleFactoryTest {

  private static final KipeExpressionModuleFactory factory = new KipeExpressionModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFiles("documentation/kipe-expression.md")
      .hasFiles("src/main/java/tech/jhipster/jhlitest/shared/kipe/package-info.java")
      .hasPrefixedFiles(
        "src/main/java/tech/jhipster/jhlitest/shared/kipe/application",
        "AccessChecker.java",
        "AccessContext.java",
        "AccessContextFactory.java",
        "AccessEvaluator.java",
        "ElementAccessContext.java",
        "KipeConfiguration.java",
        "KipeMethodSecurityExpressionHandler.java",
        "KipeMethodSecurityExpressionRoot.java",
        "NullElementAccessContext.java",
        "ObjectAccessChecker.java",
        "RolesAccessesMerger.java"
      )
      .hasPrefixedFiles(
        "src/test/java/tech/jhipster/jhlitest/shared/kipe/application",
        "AccessCheckerIT.java",
        "AccessContextFactoryTest.java",
        "AccessEvaluatorTest.java",
        "KipeApplicationService.java",
        "KipeDummyAccessChecker.java",
        "KipeIT.java",
        "ObjectAccessCheckerTest.java"
      )
      .hasPrefixedFiles("src/test/java/tech/jhipster/jhlitest/shared/kipe/domain", "KipeDummy.java", "KipeDummyChild.java");
  }
}
