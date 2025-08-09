package com.seed4j.generator.server.springboot.dbmigration.liquibase.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.DATABASE_MIGRATION;
import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.DATASOURCE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.LIQUIBASE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.LIQUIBASE_ASYNC;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.LIQUIBASE_LINTER;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.LOGS_SPY;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.MAVEN_JAVA;

import com.seed4j.generator.server.springboot.dbmigration.liquibase.application.LiquibaseApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LiquibaseModuleConfiguration {

  private static final String SPRING_BOOT_DATABASE_MIGRATION = "Spring Boot - Database Migration";

  @Bean
  SeedModuleResource liquibaseModule(LiquibaseApplicationService liquibase) {
    return SeedModuleResource.builder()
      .slug(LIQUIBASE)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addIndentation().addSpringConfigurationFormat().build())
      .apiDoc(SPRING_BOOT_DATABASE_MIGRATION, "Add Liquibase")
      .organization(SeedModuleOrganization.builder().feature(DATABASE_MIGRATION).addDependency(DATASOURCE).build())
      .tags(liquibaseTags())
      .factory(liquibase::buildModule);
  }

  private String[] liquibaseTags() {
    return new String[] { "liquibase", "database", "migration", "spring", "spring-boot" };
  }

  @Bean
  SeedModuleResource liquibaseAsyncModule(LiquibaseApplicationService liquibase) {
    return SeedModuleResource.builder()
      .slug(LIQUIBASE_ASYNC)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addIndentation().addBasePackage().addSpringConfigurationFormat().build()
      )
      .apiDoc(SPRING_BOOT_DATABASE_MIGRATION, "Support updating the database asynchronously with Liquibase")
      .organization(SeedModuleOrganization.builder().addDependency(LIQUIBASE).addDependency(LOGS_SPY).build())
      .tags(liquibaseTags())
      .factory(liquibase::buildAsyncModule);
  }

  @Bean
  SeedModuleResource liquibaseLinterModule(LiquibaseApplicationService liquibase) {
    return SeedModuleResource.builder()
      .slug(LIQUIBASE_LINTER)
      .propertiesDefinition(SeedModulePropertiesDefinition.EMPTY)
      .apiDoc(SPRING_BOOT_DATABASE_MIGRATION, "Configure a linter for the Liquibase migration scripts")
      .organization(SeedModuleOrganization.builder().addDependency(LIQUIBASE).addDependency(MAVEN_JAVA).build())
      .tags("server", "database", "migration", "liquibase", "linter")
      .factory(liquibase::buildLinterModule);
  }
}
