package com.seed4j.generator.server.springboot.technicaltools.gitinfo;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.GIT_INFORMATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_ACTUATOR;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class GitInfoModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(GIT_INFORMATION)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Tools", "Injecting Git Information into Spring")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT_ACTUATOR).build())
      .tags("server", "spring", "git", "git-information")
      .factory(new GitInfoModuleFactory()::buildModule);
  }
}
