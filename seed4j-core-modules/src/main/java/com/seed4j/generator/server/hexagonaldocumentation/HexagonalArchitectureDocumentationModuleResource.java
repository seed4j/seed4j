package com.seed4j.generator.server.hexagonaldocumentation;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.APPLICATION_SERVICE_HEXAGONAL_ARCHITECTURE_DOCUMENTATION;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class HexagonalArchitectureDocumentationModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(APPLICATION_SERVICE_HEXAGONAL_ARCHITECTURE_DOCUMENTATION)
      .withoutProperties()
      .apiDoc("Documentation", "Add documentation for hexagonal architecture")
      .standalone()
      .tags("server", "documentation")
      .factory(new HexagonalDocumentationModuleFactory()::buildModule);
  }
}
