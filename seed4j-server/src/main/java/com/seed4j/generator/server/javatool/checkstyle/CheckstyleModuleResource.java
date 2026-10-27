package com.seed4j.generator.server.javatool.checkstyle;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.CHECKSTYLE;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class CheckstyleModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(CHECKSTYLE)
      .withoutProperties()
      .apiDoc("Java", "Add Checkstyle configuration to enforce code style rules")
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).build())
      .tags("server", "tools", "checkstyle")
      .factory(new CheckstyleModuleFactory()::buildModule);
  }
}
