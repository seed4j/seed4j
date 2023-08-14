package tech.jhipster.lite.generator.server.javatool.memoizer.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class JavaMemoizersModuleFactoryTest {

  private static final JavaMemoizersModuleFactory factory = new JavaMemoizersModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFiles("src/main/java/com/jhipster/test/shared/memoizer/package-info.java")
      .hasFiles("src/main/java/com/jhipster/test/shared/memoizer/domain/Memoizers.java")
      .hasFiles("src/test/java/com/jhipster/test/shared/memoizer/domain/MemoizersTest.java");
  }
}
