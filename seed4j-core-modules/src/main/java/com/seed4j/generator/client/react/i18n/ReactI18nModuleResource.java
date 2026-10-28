package com.seed4j.generator.client.react.i18n;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_INTERNATIONALIZATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.REACT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.REACT_I18N;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class ReactI18nModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(REACT_I18N)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().build())
      .apiDoc("Frontend - React", "Add react internationalization")
      .organization(Seed4JModuleOrganization.builder().feature(CLIENT_INTERNATIONALIZATION).addDependency(REACT_CORE).build())
      .tags("client", "react", "i18n")
      .factory(new ReactI18nModuleFactory()::buildModule);
  }
}
