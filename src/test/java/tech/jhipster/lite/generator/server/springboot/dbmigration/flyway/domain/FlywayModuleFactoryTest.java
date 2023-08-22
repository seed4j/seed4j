package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class FlywayModuleFactoryTest {

  private static final String INVOCATION_DATE = "2007-12-03T10:15:30.00Z";

  private static final FlywayModuleFactory factory = new FlywayModuleFactory();

  @Test
  void shouldBuildModuleInitializationModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .put("date", INVOCATION_DATE)
      .build();

    JHipsterModule module = factory.buildInitializationModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.flywaydb</groupId>
              <artifactId>flyway-core</artifactId>
            </dependency>
        """
      )
      .and()
      .hasFiles("src/main/resources/db/migration/V20071203101530__init.sql")
      .hasFile("src/main/resources/config/application.properties")
      .containing("spring.flyway.enabled=true")
      .containing("spring.flyway.locations=classpath:db/migration");
  }

  @Test
  void shouldBuildMysqlDependencyModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildMysqlDependencyModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.flywaydb</groupId>
              <artifactId>flyway-mysql</artifactId>
            </dependency>
        """
      );
  }
}
