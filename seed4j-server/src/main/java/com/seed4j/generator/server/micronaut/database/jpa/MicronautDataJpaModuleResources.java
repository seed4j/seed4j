package com.seed4j.generator.server.micronaut.database.jpa;

import static com.seed4j.generator.server.micronaut.common.domain.MicronautFeatureSlug.MICRONAUT_JPA_PERSISTENCE;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_JPA_MARIADB;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_JPA_MSSQL;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_JPA_MYSQL;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_JPA_POSTGRESQL;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class MicronautDataJpaModuleResources {

  private MicronautDataJpaModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Postgresql implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(MICRONAUT_JPA_POSTGRESQL)
        .propertiesDefinition(
          Seed4JModulePropertiesDefinition.builder()
            .addBasePackage()
            .addProjectBaseName()
            .addIndentation()
            .addSpringConfigurationFormat()
            .build()
        )
        .apiDoc("Micronaut Data JPA - PostgreSQL", "Add Micronaut Data JPA with PostgreSQL")
        .organization(Seed4JModuleOrganization.builder().feature(MICRONAUT_JPA_PERSISTENCE).addDependency(MICRONAUT).build())
        .tags("server", "micronaut", "database", "jpa", "postgresql")
        .factory(new MicronautDataJpaModuleFactory()::buildPostgreSQL);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Mysql implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(MICRONAUT_JPA_MYSQL)
        .propertiesDefinition(
          Seed4JModulePropertiesDefinition.builder()
            .addBasePackage()
            .addProjectBaseName()
            .addIndentation()
            .addSpringConfigurationFormat()
            .build()
        )
        .apiDoc("Micronaut Data JPA - MySQL", "Add Micronaut Data JPA with MySQL")
        .organization(Seed4JModuleOrganization.builder().feature(MICRONAUT_JPA_PERSISTENCE).addDependency(MICRONAUT).build())
        .tags("server", "micronaut", "database", "jpa", "mysql")
        .factory(new MicronautDataJpaModuleFactory()::buildMySQL);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Mariadb implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(MICRONAUT_JPA_MARIADB)
        .propertiesDefinition(
          Seed4JModulePropertiesDefinition.builder()
            .addBasePackage()
            .addProjectBaseName()
            .addIndentation()
            .addSpringConfigurationFormat()
            .build()
        )
        .apiDoc("Micronaut Data JPA - MariaDB", "Add Micronaut Data JPA with MariaDB")
        .organization(Seed4JModuleOrganization.builder().feature(MICRONAUT_JPA_PERSISTENCE).addDependency(MICRONAUT).build())
        .tags("server", "micronaut", "database", "jpa", "mariadb")
        .factory(new MicronautDataJpaModuleFactory()::buildMariaDB);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Mssql implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(MICRONAUT_JPA_MSSQL)
        .propertiesDefinition(
          Seed4JModulePropertiesDefinition.builder()
            .addBasePackage()
            .addProjectBaseName()
            .addIndentation()
            .addSpringConfigurationFormat()
            .build()
        )
        .apiDoc("Micronaut Data JPA - MS SQL", "Add Micronaut Data JPA with Microsoft SQL Server")
        .organization(Seed4JModuleOrganization.builder().feature(MICRONAUT_JPA_PERSISTENCE).addDependency(MICRONAUT).build())
        .tags("server", "micronaut", "database", "jpa", "mssql")
        .factory(new MicronautDataJpaModuleFactory()::buildMsSQL);
    }
  }
}
