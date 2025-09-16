package com.seed4j.generator.server.springboot.dbmigration.liquibase.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.DATABASE_MIGRATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.DATASOURCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.LIQUIBASE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.LIQUIBASE_ASYNC;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.LIQUIBASE_LINTER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.LOGS_SPY;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MAVEN_JAVA;

import com.seed4j.generator.server.springboot.dbmigration.liquibase.application.LiquibaseApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LiquibaseModuleConfiguration {

  private static final String SPRING_BOOT_DATABASE_MIGRATION = "Spring Boot - Database Migration";

  @Bean
  Seed4JModuleResource liquibaseModule(LiquibaseApplicationService liquibase) {
    return Seed4JModuleResource.builder()
      .slug(LIQUIBASE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addIndentation().addSpringConfigurationFormat().build())
      .apiDoc(SPRING_BOOT_DATABASE_MIGRATION, "Add Liquibase")
      .organization(Seed4JModuleOrganization.builder().feature(DATABASE_MIGRATION).addDependency(DATASOURCE).build())
      .tags(liquibaseTags())
      .factory(liquibase::buildModule);
  }

  private String[] liquibaseTags() {
    return new String[] { "liquibase", "database", "migration", "spring", "spring-boot" };
  }

  @Bean
  Seed4JModuleResource liquibaseAsyncModule(LiquibaseApplicationService liquibase) {
    return Seed4JModuleResource.builder()
      .slug(LIQUIBASE_ASYNC)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addIndentation().addBasePackage().addSpringConfigurationFormat().build()
      )
      .apiDoc(SPRING_BOOT_DATABASE_MIGRATION, "Support updating the database asynchronously with Liquibase")
      .organization(Seed4JModuleOrganization.builder().addDependency(LIQUIBASE).addDependency(LOGS_SPY).build())
      .tags(liquibaseTags())
      .factory(liquibase::buildAsyncModule);
  }

  @Bean
  Seed4JModuleResource liquibaseLinterModule(LiquibaseApplicationService liquibase) {
    return Seed4JModuleResource.builder()
      .slug(LIQUIBASE_LINTER)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.EMPTY)
      .apiDoc(SPRING_BOOT_DATABASE_MIGRATION, "Configure a linter for the Liquibase migration scripts")
      .organization(Seed4JModuleOrganization.builder().addDependency(LIQUIBASE).addDependency(MAVEN_JAVA).build())
      .tags("server", "database", "migration", "liquibase", "linter")
      .factory(liquibase::buildLinterModule);
  }
}
