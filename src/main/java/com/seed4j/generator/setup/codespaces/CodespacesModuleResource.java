package com.seed4j.generator.setup.codespaces;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GITHUB_CODESPACES;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class CodespacesModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(GITHUB_CODESPACES)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addServerPort().build())
      .apiDoc("Development environment", "Init GitHub Codespaces configuration files")
      .standalone()
      .tags("setup", "codespaces")
      .factory(new CodespacesModuleFactory()::buildModule);
  }
}
