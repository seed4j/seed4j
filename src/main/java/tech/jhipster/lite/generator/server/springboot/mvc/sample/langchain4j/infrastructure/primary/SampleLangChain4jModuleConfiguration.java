package tech.jhipster.lite.generator.server.springboot.mvc.sample.langchain4j.infrastructure.primary;

import static tech.jhipster.lite.shared.slug.domain.JHLiteFeatureSlug.SPRING_MVC_SERVER;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.mvc.sample.langchain4j.application.SampleLangChain4jApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class SampleLangChain4jModuleConfiguration {

  @Bean
  JHipsterModuleResource langChain4jResourceInit(SampleLangChain4jApplicationService applicationService) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_BOOT_LANGCHAIN4J_SAMPLE)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - LangChain4j", "Add LangChain4j sample")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_MVC_SERVER).addDependency(LANGCHAIN4J).build())
      .tags("spring-boot", "spring", "server", "langchain4j")
      .factory(applicationService::buildModule);
  }
}
