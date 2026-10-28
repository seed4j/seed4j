package com.seed4j.generator.setup.gitpod;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GITPOD;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class GitpodModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(GITPOD)
      .withoutProperties()
      .apiDoc("Development environment", "Init Gitpod configuration files")
      .standalone()
      .tags("setup", "gitpod")
      .factory(new GitpodModuleFactory()::buildModule);
  }
}
