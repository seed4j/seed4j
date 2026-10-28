package com.seed4j.generator.server.springboot.thymeleaf.init;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_THYMELEAF;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class SpringBootThymeleafModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_THYMELEAF)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Thymeleaf", "Add Spring Boot Thymeleaf to the project")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_SERVER).build())
      .tags("server", "spring", "spring-boot")
      .factory(new SpringBootThymeleafModuleFactory()::buildModule);
  }
}
