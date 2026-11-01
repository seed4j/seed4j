package com.seed4j.generator.server.springboot.webflux.web;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_WEBFLUX_EMPTY;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_WEBFLUX_NETTY;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class SpringBootWebfluxModuleResources {

  private SpringBootWebfluxModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Empty implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(SPRING_BOOT_WEBFLUX_EMPTY)
        .propertiesDefinition(properties())
        .apiDoc("Spring Boot - Webflux", "Empty module: do not use alone. You should add module Spring Boot Webflux Netty")
        .organization(Seed4JModuleOrganization.builder().feature(SPRING_SERVER).addDependency(SPRING_BOOT).build())
        .tags("server", "webflux", "netty")
        .factory(new SpringBootWebfluxModuleFactory()::buildEmptyModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Netty implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(SPRING_BOOT_WEBFLUX_NETTY)
        .propertiesDefinition(properties())
        .apiDoc("Spring Boot - Webflux", "Add Spring Boot Webflux Netty")
        .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT_WEBFLUX_EMPTY).build())
        .tags("server", "webflux")
        .factory(new SpringBootWebfluxModuleFactory()::buildNettyModule);
    }
  }

  private static Seed4JModulePropertiesDefinition properties() {
    return Seed4JModulePropertiesDefinition.builder()
      .addBasePackage()
      .addIndentation()
      .addServerPort()
      .addSpringConfigurationFormat()
      .build();
  }
}
