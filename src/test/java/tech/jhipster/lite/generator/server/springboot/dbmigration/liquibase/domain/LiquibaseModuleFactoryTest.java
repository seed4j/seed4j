package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class LiquibaseModuleFactoryTest {

  private static final LiquibaseModuleFactory factory = new LiquibaseModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), logbackFile(), testLogbackFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.liquibase</groupId>
              <artifactId>liquibase-core</artifactId>
              <version>${liquibase.version}</version>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>com.h2database</groupId>
              <artifactId>h2</artifactId>
              <scope>test</scope>
            </dependency>
        """
      )
      .and()
      .hasPrefixedFiles("src/main/resources/config/liquibase", "master.xml", "changelog/0000000000_example.xml")
      .hasPrefixedFiles(
        "src/main/java/com/jhipster/test/technical/infrastructure/secondary/liquibase",
        "AsyncSpringLiquibase.java",
        "LiquibaseConfiguration.java",
        "SpringLiquibaseUtil.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/jhipster/test/technical/infrastructure/secondary/liquibase",
        "AsyncSpringLiquibaseTest.java",
        "LiquibaseConfigurationIT.java",
        "SpringLiquibaseUtilTest.java"
      )
      .hasFile("src/test/resources/logback.xml")
      .containing("<logger name=\"liquibase\" level=\"WARN\" />")
      .containing("<logger name=\"LiquibaseSchemaResolver\" level=\"INFO\" />")
      .containing("<logger name=\"com.zaxxer.hikari\" level=\"WARN\" />")
      .and()
      .hasFile("src/main/resources/logback-spring.xml")
      .containing("<logger name=\"liquibase\" level=\"WARN\" />")
      .containing("<logger name=\"LiquibaseSchemaResolver\" level=\"INFO\" />")
      .containing("<logger name=\"com.zaxxer.hikari\" level=\"WARN\" />");
  }
}
