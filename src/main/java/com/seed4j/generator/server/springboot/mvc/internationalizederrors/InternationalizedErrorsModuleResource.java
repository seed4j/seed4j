package com.seed4j.generator.server.springboot.mvc.internationalizederrors;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;

@AutoService(Seed4JModuleResourceProvider.class)
public class InternationalizedErrorsModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.INTERNATIONALIZED_ERRORS)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Spring Boot", "Add internationalization for application errors")
      .organization(Seed4JModuleOrganization.builder().addDependency(Seed4JCoreModuleSlug.SPRING_BOOT_MVC_EMPTY).build())
      .tags("server", "spring")
      .factory(new InternationalizedErrorsModuleFactory()::buildModule);
  }
}
