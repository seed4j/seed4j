package com.seed4j.generator.server.springboot.mvc.sample.langchain4j.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_MVC_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.*;

import com.seed4j.generator.server.springboot.mvc.sample.langchain4j.application.SampleLangChain4jApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SampleLangChain4jModuleConfiguration {

  @Bean
  Seed4JModuleResource sampleLangChain4jModule(SampleLangChain4jApplicationService sampleLangChain4j) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_LANGCHAIN4J_SAMPLE)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - LangChain4j", "Add LangChain4j sample")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_MVC_SERVER).addDependency(LANGCHAIN4J).build())
      .tags("spring-boot", "spring", "server", "langchain4j")
      .factory(sampleLangChain4j::buildModule);
  }
}
