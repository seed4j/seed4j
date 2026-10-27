package com.seed4j.generator.server.javatool.archunit;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JAVA_ARCHUNIT;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class ArchUnitModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(JAVA_ARCHUNIT)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Java", "Add Hexagonal Arch Unit Tests to project")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_SERVER).build())
      .tags("server", "test")
      .factory(new ArchUnitModuleFactory()::buildModule);
  }
}
