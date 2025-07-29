package com.seed4j.generator.server.springboot.dbmigration.liquibase.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.DATABASE_MIGRATION;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.DATASOURCE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.LIQUIBASE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.LIQUIBASE_ASYNC;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.LIQUIBASE_LINTER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.LOGS_SPY;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.MAVEN_JAVA;

import com.seed4j.generator.server.springboot.dbmigration.liquibase.application.LiquibaseApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LiquibaseModuleConfiguration {

  private static final String SPRING_BOOT_DATABASE_MIGRATION = "Spring Boot - Database Migration";

  @Bean
  JHipsterModuleResource liquibaseModule(LiquibaseApplicationService liquibase) {
    return JHipsterModuleResource.builder()
      .slug(LIQUIBASE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addIndentation().addSpringConfigurationFormat().build())
      .apiDoc(SPRING_BOOT_DATABASE_MIGRATION, "Add Liquibase")
      .organization(JHipsterModuleOrganization.builder().feature(DATABASE_MIGRATION).addDependency(DATASOURCE).build())
      .tags(liquibaseTags())
      .factory(liquibase::buildModule);
  }

  private String[] liquibaseTags() {
    return new String[] { "liquibase", "database", "migration", "spring", "spring-boot" };
  }

  @Bean
  JHipsterModuleResource liquibaseAsyncModule(LiquibaseApplicationService liquibase) {
    return JHipsterModuleResource.builder()
      .slug(LIQUIBASE_ASYNC)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addIndentation().addBasePackage().addSpringConfigurationFormat().build()
      )
      .apiDoc(SPRING_BOOT_DATABASE_MIGRATION, "Support updating the database asynchronously with Liquibase")
      .organization(JHipsterModuleOrganization.builder().addDependency(LIQUIBASE).addDependency(LOGS_SPY).build())
      .tags(liquibaseTags())
      .factory(liquibase::buildAsyncModule);
  }

  @Bean
  JHipsterModuleResource liquibaseLinterModule(LiquibaseApplicationService liquibase) {
    return JHipsterModuleResource.builder()
      .slug(LIQUIBASE_LINTER)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.EMPTY)
      .apiDoc(SPRING_BOOT_DATABASE_MIGRATION, "Configure a linter for the Liquibase migration scripts")
      .organization(JHipsterModuleOrganization.builder().addDependency(LIQUIBASE).addDependency(MAVEN_JAVA).build())
      .tags("server", "database", "migration", "liquibase", "linter")
      .factory(liquibase::buildLinterModule);
  }
}
