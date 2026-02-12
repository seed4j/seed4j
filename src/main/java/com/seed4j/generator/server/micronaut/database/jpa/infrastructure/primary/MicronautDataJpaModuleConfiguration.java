package com.seed4j.generator.server.micronaut.database.jpa.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MICRONAUT;

import com.seed4j.generator.server.micronaut.database.jpa.application.MicronautDataJpaApplicationService;
import com.seed4j.generator.server.micronaut.shared.MicronautModuleSlug;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MicronautDataJpaModuleConfiguration {

  @Bean
  Seed4JModuleResource micronautJpaPostgreSQLModule(MicronautDataJpaApplicationService jpa) {
    return Seed4JModuleResource.builder()
      .slug(new MicronautModuleSlug("micronaut-jpa-postgresql"))
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Micronaut Data JPA - PostgreSQL", "Add Micronaut Data JPA with PostgreSQL")
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).addDependency(MICRONAUT).build())
      .tags("server", "micronaut", "database", "jpa", "postgresql")
      .factory(jpa::buildPostgreSQLModule);
  }

  @Bean
  Seed4JModuleResource micronautJpaMySQLModule(MicronautDataJpaApplicationService jpa) {
    return Seed4JModuleResource.builder()
      .slug(new MicronautModuleSlug("micronaut-jpa-mysql"))
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Micronaut Data JPA - MySQL", "Add Micronaut Data JPA with MySQL")
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).addDependency(MICRONAUT).build())
      .tags("server", "micronaut", "database", "jpa", "mysql")
      .factory(jpa::buildMySQLModule);
  }

  @Bean
  Seed4JModuleResource micronautJpaMariaDBModule(MicronautDataJpaApplicationService jpa) {
    return Seed4JModuleResource.builder()
      .slug(new MicronautModuleSlug("micronaut-jpa-mariadb"))
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Micronaut Data JPA - MariaDB", "Add Micronaut Data JPA with MariaDB")
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).addDependency(MICRONAUT).build())
      .tags("server", "micronaut", "database", "jpa", "mariadb")
      .factory(jpa::buildMariaDBModule);
  }

  @Bean
  Seed4JModuleResource micronautJpaMsSQLModule(MicronautDataJpaApplicationService jpa) {
    return Seed4JModuleResource.builder()
      .slug(new MicronautModuleSlug("micronaut-jpa-mssql"))
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Micronaut Data JPA - MS SQL", "Add Micronaut Data JPA with Microsoft SQL Server")
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).addDependency(MICRONAUT).build())
      .tags("server", "micronaut", "database", "jpa", "mssql")
      .factory(jpa::buildMsSQLModule);
  }
}
