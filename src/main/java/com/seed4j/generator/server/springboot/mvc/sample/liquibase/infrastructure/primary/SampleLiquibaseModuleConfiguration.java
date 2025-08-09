package com.seed4j.generator.server.springboot.mvc.sample.liquibase.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SAMPLE_SCHEMA;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.LIQUIBASE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SAMPLE_FEATURE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SAMPLE_LIQUIBASE_CHANGELOG;

import com.seed4j.generator.server.springboot.mvc.sample.liquibase.application.SampleLiquibaseApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SampleLiquibaseModuleConfiguration {

  @Bean
  SeedModuleResource sampleLiquibaseModule(SampleLiquibaseApplicationService sampleLiquibase) {
    return SeedModuleResource.builder()
      .slug(SAMPLE_LIQUIBASE_CHANGELOG)
      .withoutProperties()
      .apiDoc("Sample Feature", "Add liquibase changelog for sample feature")
      .organization(SeedModuleOrganization.builder().feature(SAMPLE_SCHEMA).addDependency(LIQUIBASE).addDependency(SAMPLE_FEATURE).build())
      .tags("server")
      .factory(sampleLiquibase::buildModule);
  }
}
