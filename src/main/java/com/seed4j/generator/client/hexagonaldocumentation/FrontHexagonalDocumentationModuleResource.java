package com.seed4j.generator.client.hexagonaldocumentation;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;

@AutoService(Seed4JModuleResourceProvider.class)
public class FrontHexagonalDocumentationModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.FRONT_HEXAGONAL_ARCHITECTURE)
      .withoutProperties()
      .apiDoc("Frontend", "Add front hexagonal architecture documentation")
      .standalone()
      .tags("client", "documentation")
      .factory(new FrontHexagonalDocumentationModuleFactory()::buildModule);
  }
}
