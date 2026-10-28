package com.seed4j.generator.client.angular.i18n;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_INTERNATIONALIZATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_I18N;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class AngularI18nModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(ANGULAR_I18N)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().build())
      .apiDoc("Frontend - Angular", "Add Angular internationalization")
      .organization(Seed4JModuleOrganization.builder().feature(CLIENT_INTERNATIONALIZATION).addDependency(ANGULAR_CORE).build())
      .tags("client", "angular", "i18n")
      .factory(new AngularI18nModuleFactory()::buildModule);
  }
}
