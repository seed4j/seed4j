package com.seed4j.generator.setup.aicontext.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.AI_CONTEXT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JAVA_BASE;

import com.seed4j.generator.setup.aicontext.application.AiContextApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AiContextModuleConfiguration {

  @Bean
  Seed4JModuleResource aiContextModule(AiContextApplicationService aiContext) {
    return Seed4JModuleResource.builder()
      .slug(AI_CONTEXT)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectName().build())
      .apiDoc(
        "AI agents",
        "Add AGENTS.md, CLAUDE.md and an .agents folder with the project context and business skills for AI agents (replaces existing AGENTS.md and CLAUDE.md)"
      )
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BASE).build())
      .tags("setup", "ai", "agent")
      .factory(aiContext::buildModule);
  }
}
