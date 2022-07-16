package tech.jhipster.lite.generator.server.springboot.mvc.zalandoproblem.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.mvc.zalandoproblem.application.ZalandoProblemsApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class ZalandoProblemsModuleConfiguration {

  @Bean
  JHipsterModuleResource zalandoProblemsModule(ZalandoProblemsApplicationService zalandoProblems) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/zalando-problems")
      .slug("zalando-problems")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - MVC", "Zalando problems and error handler"))
      .tags("server", "spring", "spring-boot", "mvc", "problem")
      .factory(zalandoProblems::buildModule);
  }
}
