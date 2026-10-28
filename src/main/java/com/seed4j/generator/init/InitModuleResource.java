package com.seed4j.generator.init;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.INIT;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class InitModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(INIT)
      .propertiesDefinition(initPropertiesDefinition())
      .apiDoc("Init", "Init project")
      .standalone()
      .tags("server", "init")
      .factory(new InitModuleFactory(services)::buildModule);
  }

  private static Seed4JModulePropertiesDefinition initPropertiesDefinition() {
    return Seed4JModulePropertiesDefinition.builder()
      .addProjectBaseName()
      .addProjectName()
      .addEndOfLine()
      .addIndentation()
      .addNodePackageManager()
      .build();
  }
}
