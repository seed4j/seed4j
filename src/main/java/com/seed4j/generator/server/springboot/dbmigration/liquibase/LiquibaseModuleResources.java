package com.seed4j.generator.server.springboot.dbmigration.liquibase;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.DATABASE_MIGRATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.DATASOURCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.LIQUIBASE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.LIQUIBASE_ASYNC;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.LIQUIBASE_LINTER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.LOGS_SPY;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MAVEN_JAVA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_ASYNC;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class LiquibaseModuleResources {

  private static final String SPRING_BOOT_DATABASE_MIGRATION = "Spring Boot - Database Migration";

  private LiquibaseModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Liquibase implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(LIQUIBASE)
        .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addIndentation().addSpringConfigurationFormat().build())
        .apiDoc(SPRING_BOOT_DATABASE_MIGRATION, "Add Liquibase")
        .organization(Seed4JModuleOrganization.builder().feature(DATABASE_MIGRATION).addDependency(DATASOURCE).build())
        .tags(liquibaseTags())
        .factory(new LiquibaseModuleFactory()::buildModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Async implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(LIQUIBASE_ASYNC)
        .propertiesDefinition(
          Seed4JModulePropertiesDefinition.builder().addIndentation().addBasePackage().addSpringConfigurationFormat().build()
        )
        .apiDoc(SPRING_BOOT_DATABASE_MIGRATION, "Support updating the database asynchronously with Liquibase")
        .organization(
          Seed4JModuleOrganization.builder().addDependency(LIQUIBASE).addDependency(LOGS_SPY).addDependency(SPRING_BOOT_ASYNC).build()
        )
        .tags(liquibaseTags())
        .factory(new LiquibaseModuleFactory()::buildAsyncModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Linter implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(LIQUIBASE_LINTER)
        .propertiesDefinition(Seed4JModulePropertiesDefinition.EMPTY)
        .apiDoc(SPRING_BOOT_DATABASE_MIGRATION, "Configure a linter for the Liquibase migration scripts")
        .organization(Seed4JModuleOrganization.builder().addDependency(LIQUIBASE).addDependency(MAVEN_JAVA).build())
        .tags("server", "database", "migration", "liquibase", "linter")
        .factory(new LiquibaseModuleFactory()::buildLinterModule);
    }
  }

  private static String[] liquibaseTags() {
    return new String[] { "liquibase", "database", "migration", "spring", "spring-boot" };
  }
}
