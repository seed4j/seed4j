package com.seed4j.generator.server.springboot.mvc.security.kipe.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

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
        "ObjectAccessChecker.java"
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
