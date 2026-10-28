package com.seed4j.generator.server.micronaut.sample.feature;

import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_JWT;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_OPENAPI;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_SAMPLE_FEATURE;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class MicronautSampleFeatureModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(MICRONAUT_SAMPLE_FEATURE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc("Micronaut Sample Feature", "Add sample context with some APIs for Micronaut")
      .organization(
        Seed4JModuleOrganization.builder().addDependency(MICRONAUT).addDependency(MICRONAUT_OPENAPI).addDependency(MICRONAUT_JWT).build()
      )
      .tags("server", "micronaut", "sample")
      .factory(new MicronautSampleFeatureModuleFactory()::buildModule);
  }
}
