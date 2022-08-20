package tech.jhipster.lite.generator.infinitest.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.infinitest.application.InfinitestApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class InfinitestModuleConfiguration {

  @Bean
  JHipsterModuleResource infinitestModule(InfinitestApplicationService infinitest) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/infinitest-filters")
      .slug("infinitest-filters")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc("Base", "Add filter for infinitest"))
      .organization(JHipsterModuleOrganization.builder().addModuleDependency("git-init").build())
      .tags("server", "init", "test")
      .factory(infinitest::build);
  }
}
