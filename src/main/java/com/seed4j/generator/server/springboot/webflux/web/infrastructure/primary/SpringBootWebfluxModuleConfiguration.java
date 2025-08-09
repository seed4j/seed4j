package com.seed4j.generator.server.springboot.webflux.web.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRING_SERVER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_WEBFLUX_EMPTY;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_WEBFLUX_NETTY;

import com.seed4j.generator.server.springboot.webflux.web.application.SpringBootWebfluxApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringBootWebfluxModuleConfiguration {

  @Bean
  SeedModuleResource springBootWebfluxModule(SpringBootWebfluxApplicationService webflux) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_WEBFLUX_EMPTY)
      .propertiesDefinition(properties())
      .apiDoc("Spring Boot - Webflux", "Empty module: do not use alone. You should add module Spring Boot Webflux Netty")
      .organization(SeedModuleOrganization.builder().feature(SPRING_SERVER).addDependency(SPRING_BOOT).build())
      .tags("server", "webflux", "netty")
      .factory(webflux::buildEmptyModule);
  }

  @Bean
  SeedModuleResource springBootWebfluxNettyModule(SpringBootWebfluxApplicationService webflux) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_WEBFLUX_NETTY)
      .propertiesDefinition(properties())
      .apiDoc("Spring Boot - Webflux", "Add Spring Boot Webflux Netty")
      .organization(SeedModuleOrganization.builder().addDependency(SPRING_BOOT_WEBFLUX_EMPTY).build())
      .tags("server", "webflux")
      .factory(webflux::buildNettyModule);
  }

  private static SeedModulePropertiesDefinition properties() {
    return SeedModulePropertiesDefinition.builder()
      .addBasePackage()
      .addIndentation()
      .addServerPort()
      .addSpringConfigurationFormat()
      .build();
  }
}
