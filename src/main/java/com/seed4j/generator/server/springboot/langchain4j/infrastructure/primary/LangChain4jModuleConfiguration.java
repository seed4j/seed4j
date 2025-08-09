package com.seed4j.generator.server.springboot.langchain4j.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.LANGCHAIN4J;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT;

import com.seed4j.generator.server.springboot.langchain4j.application.LangChain4jApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LangChain4jModuleConfiguration {

  @Bean
  SeedModuleResource langChain4jModule(LangChain4jApplicationService langChain4j) {
    return SeedModuleResource.builder()
      .slug(LANGCHAIN4J)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build())
      .apiDoc("LangChain4j", "Add LangChain4j")
      .organization(SeedModuleOrganization.builder().addDependency(SPRING_BOOT).build())
      .tags("server", "spring", "spring-boot", "langchain4j")
      .factory(langChain4j::buildModule);
  }
}
