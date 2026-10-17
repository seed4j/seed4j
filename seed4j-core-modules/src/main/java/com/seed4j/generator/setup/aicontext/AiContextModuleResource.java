package com.seed4j.generator.setup.aicontext;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.AI_CONTEXT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JAVA_BASE;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class AiContextModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(AI_CONTEXT)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectName().build())
      .apiDoc(
        "AI agents",
        "Add AGENTS.md, CLAUDE.md and an .agents folder with the project context and business skills for AI agents (replaces existing AGENTS.md and CLAUDE.md)"
      )
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BASE).build())
      .tags("setup", "ai", "agent")
      .factory(new AiContextModuleFactory()::buildModule);
  }
}
