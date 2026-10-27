package com.seed4j.generator.server.springboot.database.datasource;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.DATASOURCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_MARIADB;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_MSSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_MYSQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.DATASOURCE_POSTGRESQL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class DatasourceModuleResources {

  private static final String API_DOC_GROUP = "Spring Boot - Database";

  private DatasourceModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Postgresql implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(DATASOURCE_POSTGRESQL)
        .propertiesDefinition(properties())
        .apiDoc(API_DOC_GROUP, "Add PostgreSQL datasource to Spring project")
        .organization(organization())
        .tags(tags())
        .factory(new DatasourceModuleFactory(services)::buildPostgreSQL);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Mariadb implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(DATASOURCE_MARIADB)
        .propertiesDefinition(properties())
        .apiDoc(API_DOC_GROUP, "Add MariaDB datasource to Spring project")
        .organization(organization())
        .tags(tags())
        .factory(new DatasourceModuleFactory(services)::buildMariaDB);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Mysql implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(DATASOURCE_MYSQL)
        .propertiesDefinition(properties())
        .apiDoc(API_DOC_GROUP, "Add MySQL datasource to Spring project")
        .organization(organization())
        .tags(tags())
        .factory(new DatasourceModuleFactory(services)::buildMySQL);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Mssql implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(DATASOURCE_MSSQL)
        .propertiesDefinition(properties())
        .apiDoc(API_DOC_GROUP, "Add MsSQL datasource to Spring project")
        .organization(organization())
        .tags(tags())
        .factory(new DatasourceModuleFactory(services)::buildMsSQL);
    }
  }

  private static Seed4JModulePropertiesDefinition properties() {
    return Seed4JModulePropertiesDefinition.builder().addIndentation().addProjectBaseName().addSpringConfigurationFormat().build();
  }

  private static Seed4JModuleOrganization organization() {
    return Seed4JModuleOrganization.builder().feature(DATASOURCE).addDependency(SPRING_BOOT).build();
  }

  private static String[] tags() {
    return new String[] { "server", "spring", "spring-boot", "datasource", "database" };
  }
}
