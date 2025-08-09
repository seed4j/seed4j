package com.seed4j.generator.server.springboot.dbmigration.liquibase.domain;

import static com.seed4j.TestFileUtils.*;
import static com.seed4j.module.domain.properties.SpringConfigurationFormat.*;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class LiquibaseModuleFactoryTest {

  private static final LiquibaseModuleFactory factory = new LiquibaseModuleFactory();

  @Nested
  class LiquibaseModule {

    @Test
    void shouldBuildModule() {
      SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(tmpDirForTest()).basePackage("com.seed4j.growth").build();

      SeedModule module = factory.buildModule(properties);

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
        .and()
        .hasPrefixedFiles("src/main/resources/config/liquibase", "master.xml", "changelog/0000000000_example.xml")
        .hasFile("src/test/resources/logback.xml")
        .containing("<logger name=\"liquibase\" level=\"WARN\" />")
        .containing("<logger name=\"LiquibaseSchemaResolver\" level=\"INFO\" />")
        .containing("<logger name=\"com.zaxxer.hikari\" level=\"WARN\" />")
        .and()
        .hasFile("src/main/resources/logback-spring.xml")
        .containing("<logger name=\"liquibase\" level=\"WARN\" />")
        .containing("<logger name=\"LiquibaseSchemaResolver\" level=\"INFO\" />")
        .containing("<logger name=\"com.zaxxer.hikari\" level=\"WARN\" />")
        .and()
        .hasFile("src/main/resources/config/application.yml")
        .containing(
          """
          spring:
            liquibase:
              change-log: classpath:config/liquibase/master.xml
          """
        )
        .and()
        .hasFile("src/test/resources/config/application-test.yml")
        .containing(
          """
          spring:
            liquibase:
              contexts: test
          """
        );
    }
  }

  @Nested
  class AsyncLiquibaseModule {

    @Test
    void shouldBuildAsyncModule() {
      SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(tmpDirForTest()).basePackage("com.seed4j.growth").build();

      SeedModule module = factory.buildAsyncModule(properties);

      assertThatModuleWithFiles(module, pomFile(), logbackFile(), testLogbackFile())
        .hasPrefixedFiles(
          "src/main/java/com/seed4j/growth/wire/liquibase/infrastructure/secondary",
          "AsyncSpringLiquibase.java",
          "LiquibaseConfiguration.java",
          "SpringLiquibaseUtil.java"
        )
        .hasPrefixedFiles(
          "src/test/java/com/seed4j/growth/wire/liquibase/infrastructure/secondary",
          "AsyncSpringLiquibaseTest.java",
          "LiquibaseConfigurationIT.java",
          "SpringLiquibaseUtilTest.java"
        );
    }

    @Test
    void shouldBuildModuleWithYamlSpringConfigurationFormat() {
      SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(tmpDirForTest())
        .basePackage("com.seed4j.growth")
        .springConfigurationFormat(YAML)
        .build();

      SeedModule module = factory.buildAsyncModule(properties);

      assertThatModuleWithFiles(module, pomFile(), logbackFile(), testLogbackFile())
        .hasFile("src/test/java/com/seed4j/growth/wire/liquibase/infrastructure/secondary/SpringLiquibaseUtilTest.java")
        .containing("var yaml = new YamlPropertiesFactoryBean();")
        .notContaining("var properties = new Properties();");
    }

    @Test
    void shouldBuildModuleWithPropertiesSpringConfigurationFormat() {
      SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(tmpDirForTest())
        .basePackage("com.seed4j.growth")
        .springConfigurationFormat(PROPERTIES)
        .build();

      SeedModule module = factory.buildAsyncModule(properties);

      assertThatModuleWithFiles(module, pomFile(), logbackFile(), testLogbackFile())
        .hasFile("src/test/java/com/seed4j/growth/wire/liquibase/infrastructure/secondary/SpringLiquibaseUtilTest.java")
        .containing("var properties = new Properties();")
        .notContaining("var yaml = new YamlPropertiesFactoryBean();");
    }
  }

  @Nested
  class LiquibaseLinterModule {

    @Test
    void shouldBuildModule() {
      SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(tmpDirForTest()).basePackage("com.seed4j.growth").build();

      SeedModule module = factory.buildLinterModule(properties);

      assertThatModuleWithFiles(module, pomFile())
        .hasFile("pom.xml")
        .containing(
          """
                <plugin>
                  <groupId>io.github.liquibase-linter</groupId>
                  <artifactId>liquibase-linter-maven-plugin</artifactId>
                </plugin>
          """
        )
        .containing(
          """
                  <plugin>
                    <groupId>io.github.liquibase-linter</groupId>
                    <artifactId>liquibase-linter-maven-plugin</artifactId>
                    <version>${liquibase-linter-maven-plugin.version}</version>
                    <executions>
                      <execution>
                        <goals>
                          <goal>lint</goal>
                        </goals>
                      </execution>
                    </executions>
                    <configuration>
                      <changeLogFile>src/main/resources/config/liquibase/master.xml</changeLogFile>
                      <configurationFile>src/test/resources/liquibase-linter.jsonc</configurationFile>
                    </configuration>
                  </plugin>
          """
        )
        .and()
        .hasFile("src/test/resources/liquibase-linter.jsonc");
    }
  }
}
