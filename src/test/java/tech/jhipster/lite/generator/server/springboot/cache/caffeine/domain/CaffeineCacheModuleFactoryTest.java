package tech.jhipster.lite.generator.server.springboot.cache.caffeine.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class CaffeineCacheModuleFactoryTest {

  private static final CaffeineCacheModuleFactory factory = new CaffeineCacheModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>com.github.ben-manes.caffeine</groupId>
              <artifactId>jcache</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>javax.cache</groupId>
              <artifactId>cache-api</artifactId>
            </dependency>
        """
      )
      .and()
      .hasFiles("documentation/caffeine.md");
  }
}
