package com.seed4j.generator.server.springboot.cache.caffeine;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;

@AutoService(Seed4JModuleResourceProvider.class)
public class CaffeineCacheModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.CAFFEINE_CACHE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - Cache", "Add caffeine cache")
      .organization(Seed4JModuleOrganization.builder().addDependency(Seed4JCoreModuleSlug.SPRING_BOOT_CACHE).build())
      .tags("server", "spring", "spring-boot", "cache")
      .factory(new CaffeineCacheModuleFactory()::buildModule);
  }
}
