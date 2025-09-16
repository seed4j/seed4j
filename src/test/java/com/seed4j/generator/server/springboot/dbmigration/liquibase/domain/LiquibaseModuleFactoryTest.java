package com.seed4j.generator.server.springboot.dbmigration.liquibase.domain;

import static com.seed4j.TestFileUtils.*;
import static com.seed4j.module.domain.properties.SpringConfigurationFormat.*;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class LiquibaseModuleFactoryTest {

  private static final LiquibaseModuleFactory factory = new LiquibaseModuleFactory();

  @Nested
  class LiquibaseModule {

    @Test
    void shouldBuildModule() {
      Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(tmpDirForTest()).basePackage("com.seed4j.growth").build();

      Seed4JModule module = factory.buildModule(properties);

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
      Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(tmpDirForTest()).basePackage("com.seed4j.growth").build();

      Seed4JModule module = factory.buildAsyncModule(properties);

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
      Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(tmpDirForTest())
        .basePackage("com.seed4j.growth")
        .springConfigurationFormat(YAML)
        .build();

      Seed4JModule module = factory.buildAsyncModule(properties);

      assertThatModuleWithFiles(module, pomFile(), logbackFile(), testLogbackFile())
        .hasFile("src/test/java/com/seed4j/growth/wire/liquibase/infrastructure/secondary/SpringLiquibaseUtilTest.java")
        .containing("var yaml = new YamlPropertiesFactoryBean();")
        .notContaining("var properties = new Properties();");
    }

    @Test
    void shouldBuildModuleWithPropertiesSpringConfigurationFormat() {
      Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(tmpDirForTest())
        .basePackage("com.seed4j.growth")
        .springConfigurationFormat(PROPERTIES)
        .build();

      Seed4JModule module = factory.buildAsyncModule(properties);

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
      Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(tmpDirForTest()).basePackage("com.seed4j.growth").build();

      Seed4JModule module = factory.buildLinterModule(properties);

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
