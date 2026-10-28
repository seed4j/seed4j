package com.seed4j.generator.server.springboot.mvc.sample.feature;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CUCUMBER_AUTHENTICATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRINGDOC;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JAVA_BASE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.KIPE_AUTHORIZATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.KIPE_EXPRESSION;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SAMPLE_FEATURE;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class SampleFeatureModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(SAMPLE_FEATURE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc("Sample Feature", "Add sample context with some APIs")
      .organization(
        Seed4JModuleOrganization.builder()
          .addDependency(CUCUMBER_AUTHENTICATION)
          .addDependency(SPRINGDOC)
          .addDependency(JAVA_BASE)
          .addDependency(KIPE_EXPRESSION)
          .addDependency(KIPE_AUTHORIZATION)
          .build()
      )
      .tags("server")
      .factory(new SampleFeatureModuleFactory()::buildModule);
  }
}
