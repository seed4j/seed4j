package tech.jhipster.lite.generator.infinitest.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.infinitest.application.InfinitestApplicationService;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class InfinitestConfiguration {

  @Bean
  JHipsterModuleResource infinitestModule(InfinitestApplicationService infinitest) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/infinitest-filters")
      .slug("infinitest-filters")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc("Base", "Add filter for infinitest"))
      .tags("server", "init", "test")
      .factory(infinitest::build);
  }
}
