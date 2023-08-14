package tech.jhipster.lite.generator.server.springboot.dbmigration.mongock.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class MongockModuleFactoryTest {

  private static final MongockModuleFactory factory = new MongockModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFiles("documentation/mongock.md")
      .hasFile("pom.xml")
      .containing(
        """
              <dependency>
                <groupId>io.mongock</groupId>
                <artifactId>mongock-bom</artifactId>
                <version>${mongock.version}</version>
                <scope>import</scope>
                <type>pom</type>
              </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>io.mongock</groupId>
              <artifactId>mongock-springboot</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>io.mongock</groupId>
              <artifactId>mongodb-springdata-v4-driver</artifactId>
            </dependency>
        """
      )
      .and()
      .hasFiles("src/main/java/com/jhipster/test/wire/mongock/infrastructure/secondary/MongockDatabaseConfiguration.java")
      .hasFile("src/main/resources/config/application.properties")
      .containing("mongock.migration-scan-package=com.jhipster.test")
      .and()
      .hasFile("src/test/resources/config/application.properties")
      .containing("mongock.migration-scan-package=com.jhipster.test");
  }
}
