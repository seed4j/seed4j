package com.seed4j.generator.server.springboot.mvc.sample.langchain4j;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_MVC_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.*;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class SampleLangChain4jModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_LANGCHAIN4J_SAMPLE)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - LangChain4j", "Add LangChain4j sample")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_MVC_SERVER).addDependency(LANGCHAIN4J).build())
      .tags("spring-boot", "spring", "server", "langchain4j")
      .factory(new SampleLangChain4jModuleFactory()::buildModule);
  }
}
