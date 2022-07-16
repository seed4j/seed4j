package tech.jhipster.lite.generator.server.springboot.beanvalidationtest.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.beanvalidationtest.application.BeanValidationTestApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class BeanValidationTestModuleConfiguration {

  @Bean
  JHipsterModuleResource beanValidationtTestModule(BeanValidationTestApplicationService beanValidationTest) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/bean-validation-test")
      .slug("bean-validation-test")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot", "Add test tools for bean validation"))
      .tags("server", "spring", "spring-boot", "validation")
      .factory(beanValidationTest::buildModule);
  }
}
