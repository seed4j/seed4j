package com.seed4j.generator.server.springboot.mvc.sample.jpapersistence;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SAMPLE_PERSISTENCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SAMPLE_SCHEMA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SAMPLE_JPA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_CUCUMBER_JPA_RESET;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class SampleJpaPersistenceModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(SAMPLE_JPA_PERSISTENCE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Sample Feature", "Add JPA persistence for sample feature")
      .organization(
        Seed4JModuleOrganization.builder()
          .feature(SAMPLE_PERSISTENCE)
          .addDependency(SAMPLE_SCHEMA)
          .addDependency(SPRING_BOOT_CUCUMBER_JPA_RESET)
          .build()
      )
      .tags("server")
      .factory(new SampleJpaPersistenceModuleFactory()::buildModule);
  }
}
