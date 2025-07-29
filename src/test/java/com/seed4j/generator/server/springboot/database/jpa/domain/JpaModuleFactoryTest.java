package com.seed4j.generator.server.springboot.database.jpa.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class JpaModuleFactoryTest {

  private final JpaModuleFactory factory = new JpaModuleFactory();

  @Test
  void shouldBuildPostgreSQLModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildPostgreSQL(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("src/main/java/tech/jhipster/jhlitest/wire/database/infrastructure/secondary/DatabaseConfiguration.java")
      .containing("package tech.jhipster.jhlitest.wire.database.infrastructure.secondary;")
      .and()
      .hasFile("pom.xml")
      .containing(
        """
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-data-jpa</artifactId>\
        """
      )
      .containing(
        """
              <groupId>org.hibernate.orm</groupId>
              <artifactId>hibernate-core</artifactId>\
        """
      )
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        spring:
          data:
            jpa:
              repositories:
                bootstrap-mode: deferred
          jpa:
            hibernate:
              ddl-auto: none
              naming:
                implicit-strategy: org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
                physical-strategy: org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
            open-in-view: false
            properties:
              hibernate:
                connection:
                  provider_disables_autocommit: true
                generate_statistics: false
                jdbc:
                  batch_size: 25
                  time_zone: UTC
                order_inserts: true
                order_updates: true
                query:
                  fail_on_pagination_over_collection_fetch: true
                  in_clause_parameter_padding: true
        """
      );
  }

  @Test
  void shouldBuildMariadbModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildMariaDB(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("src/main/java/tech/jhipster/jhlitest/wire/database/infrastructure/secondary/DatabaseConfiguration.java")
      .containing("package tech.jhipster.jhlitest.wire.database.infrastructure.secondary;")
      .and()
      .hasFile("pom.xml")
      .containing(
        """
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-data-jpa</artifactId>\
        """
      )
      .containing(
        """
              <groupId>org.hibernate.orm</groupId>
              <artifactId>hibernate-core</artifactId>\
        """
      )
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        spring:
          data:
            jpa:
              repositories:
                bootstrap-mode: deferred
          jpa:
            hibernate:
              ddl-auto: none
              naming:
                implicit-strategy: org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
                physical-strategy: org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
            open-in-view: false
            properties:
              hibernate:
                connection:
                  provider_disables_autocommit: true
                generate_statistics: false
                jdbc:
                  batch_size: 25
                  time_zone: UTC
                order_inserts: true
                order_updates: true
                query:
                  fail_on_pagination_over_collection_fetch: true
                  in_clause_parameter_padding: true
        """
      );
  }

  @Test
  void shouldBuildMysqlModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildMySQL(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("src/main/java/tech/jhipster/jhlitest/wire/database/infrastructure/secondary/DatabaseConfiguration.java")
      .containing("package tech.jhipster.jhlitest.wire.database.infrastructure.secondary;")
      .and()
      .hasFile("pom.xml")
      .containing(
        """
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-data-jpa</artifactId>\
        """
      )
      .containing(
        """
              <groupId>org.hibernate.orm</groupId>
              <artifactId>hibernate-core</artifactId>\
        """
      )
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        // language=yaml
        """
        spring:
          data:
            jpa:
              repositories:
                bootstrap-mode: deferred
          jpa:
            hibernate:
              ddl-auto: none
              naming:
                implicit-strategy: org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
                physical-strategy: org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
            open-in-view: false
            properties:
              hibernate:
                connection:
                  provider_disables_autocommit: true
                generate_statistics: false
                jdbc:
                  batch_size: 25
                  time_zone: UTC
                order_inserts: true
                order_updates: true
                query:
                  fail_on_pagination_over_collection_fetch: true
                  in_clause_parameter_padding: true
        """
      );
  }

  @Test
  void shouldBuildMssqlModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildMsSQL(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("src/main/java/tech/jhipster/jhlitest/wire/database/infrastructure/secondary/DatabaseConfiguration.java")
      .containing("package tech.jhipster.jhlitest.wire.database.infrastructure.secondary;")
      .and()
      .hasFile("pom.xml")
      .containing(
        """
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-data-jpa</artifactId>\
        """
      )
      .containing(
        """
              <groupId>org.hibernate.orm</groupId>
              <artifactId>hibernate-core</artifactId>\
        """
      )
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        // language=yaml
        """
        spring:
          data:
            jpa:
              repositories:
                bootstrap-mode: deferred
          jpa:
            hibernate:
              ddl-auto: update
              naming:
                implicit-strategy: org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
                physical-strategy: org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
            open-in-view: false
            properties:
              hibernate:
                connection:
                  provider_disables_autocommit: true
                criteria:
                  literal_handling_mode: BIND
                dialect: org.hibernate.dialect.SQLServer2012Dialect
                format_sql: true
                generate_statistics: false
                jdbc:
                  batch_size: 25
                  fetch_size: 150
                  time_zone: UTC
                order_inserts: true
                order_updates: true
                query:
                  fail_on_pagination_over_collection_fetch: true
                  in_clause_parameter_padding: true
        """
      );
  }
}
