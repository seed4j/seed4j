package com.seed4j.generator.server.springboot.mvc.sample.liquibase.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SAMPLE_SCHEMA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.LIQUIBASE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SAMPLE_FEATURE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SAMPLE_LIQUIBASE_CHANGELOG;

import com.seed4j.generator.server.springboot.mvc.sample.liquibase.application.SampleLiquibaseApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SampleLiquibaseModuleConfiguration {

  @Bean
  Seed4JModuleResource sampleLiquibaseModule(SampleLiquibaseApplicationService sampleLiquibase) {
    return Seed4JModuleResource.builder()
      .slug(SAMPLE_LIQUIBASE_CHANGELOG)
      .withoutProperties()
      .apiDoc("Sample Feature", "Add liquibase changelog for sample feature")
      .organization(
        Seed4JModuleOrganization.builder().feature(SAMPLE_SCHEMA).addDependency(LIQUIBASE).addDependency(SAMPLE_FEATURE).build()
      )
      .tags("server")
      .factory(sampleLiquibase::buildModule);
  }
}
