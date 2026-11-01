package com.seed4j.generator.server.springboot.database.jpa;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JPA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_MARIADB;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_MSSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_MYSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_POSTGRESQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JPA_MARIADB;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JPA_METAMODEL_GENERATOR;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JPA_MSSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JPA_MYSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JPA_POSTGRESQL;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class JpaModuleResources {

  private static final String API_DOC_GROUP = "Spring Boot - Database";

  private JpaModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Postgresql implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(JPA_POSTGRESQL)
        .propertiesDefinition(properties())
        .apiDoc(API_DOC_GROUP, "Add JPA with PostgreSQL to project")
        .organization(Seed4JModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(DATASOURCE_POSTGRESQL).build())
        .tags(tags())
        .factory(new JpaModuleFactory()::buildPostgreSQL);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Mariadb implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(JPA_MARIADB)
        .propertiesDefinition(properties())
        .apiDoc(API_DOC_GROUP, "Add JPA with MariaDB to project")
        .organization(Seed4JModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(DATASOURCE_MARIADB).build())
        .tags(tags())
        .factory(new JpaModuleFactory()::buildMariaDB);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Mysql implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(JPA_MYSQL)
        .propertiesDefinition(properties())
        .apiDoc(API_DOC_GROUP, "Add JPA with MySQL to project")
        .organization(Seed4JModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(DATASOURCE_MYSQL).build())
        .tags(tags())
        .factory(new JpaModuleFactory()::buildMySQL);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Mssql implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(JPA_MSSQL)
        .propertiesDefinition(properties())
        .apiDoc(API_DOC_GROUP, "Add JPA with MsSQL to project")
        .organization(Seed4JModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(DATASOURCE_MSSQL).build())
        .tags(tags())
        .factory(new JpaModuleFactory()::buildMsSQL);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class MetamodelGenerator implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(JPA_METAMODEL_GENERATOR)
        .propertiesDefinition(Seed4JModulePropertiesDefinition.EMPTY)
        .apiDoc(API_DOC_GROUP, "Add JPA metamodel generator to project")
        .organization(Seed4JModuleOrganization.builder().addDependency(JPA_PERSISTENCE).build())
        .tags(tags())
        .factory(new JpaModuleFactory()::buildMetaModelGenerator);
    }
  }

  private static Seed4JModulePropertiesDefinition properties() {
    return Seed4JModulePropertiesDefinition.builder()
      .addBasePackage()
      .addIndentation()
      .addProjectBaseName()
      .addSpringConfigurationFormat()
      .build();
  }

  private static String[] tags() {
    return new String[] { "server", "spring", "spring-boot", "database" };
  }
}
