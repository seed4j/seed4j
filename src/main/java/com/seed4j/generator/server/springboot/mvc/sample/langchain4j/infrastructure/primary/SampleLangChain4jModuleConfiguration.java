package com.seed4j.generator.server.springboot.mvc.sample.langchain4j.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRING_MVC_SERVER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.*;

import com.seed4j.generator.server.springboot.mvc.sample.langchain4j.application.SampleLangChain4jApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SampleLangChain4jModuleConfiguration {

  @Bean
  SeedModuleResource sampleLangChain4jModule(SampleLangChain4jApplicationService sampleLangChain4j) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_LANGCHAIN4J_SAMPLE)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - LangChain4j", "Add LangChain4j sample")
      .organization(SeedModuleOrganization.builder().addDependency(SPRING_MVC_SERVER).addDependency(LANGCHAIN4J).build())
      .tags("spring-boot", "spring", "server", "langchain4j")
      .factory(sampleLangChain4j::buildModule);
  }
}
