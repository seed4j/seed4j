package com.seed4j.generator.server.springboot.langchain4j.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.LANGCHAIN4J;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;

import com.seed4j.generator.server.springboot.langchain4j.application.LangChain4jApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LangChain4jModuleConfiguration {

  @Bean
  Seed4JModuleResource langChain4jModule(LangChain4jApplicationService langChain4j) {
    return Seed4JModuleResource.builder()
      .slug(LANGCHAIN4J)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build())
      .apiDoc("LangChain4j", "Add LangChain4j")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT).build())
      .tags("server", "spring", "spring-boot", "langchain4j")
      .factory(langChain4j::buildModule);
  }
}
