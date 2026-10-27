package com.seed4j.generator.server.springboot.langchain4j;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.LANGCHAIN4J;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class LangChain4jModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(LANGCHAIN4J)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build())
      .apiDoc("LangChain4j", "Add LangChain4j")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT).build())
      .tags("server", "spring", "spring-boot", "langchain4j")
      .factory(new LangChain4jModuleFactory()::buildModule);
  }
}
