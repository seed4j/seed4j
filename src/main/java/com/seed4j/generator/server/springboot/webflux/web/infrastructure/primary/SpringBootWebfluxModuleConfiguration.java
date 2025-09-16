package com.seed4j.generator.server.springboot.webflux.web.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_WEBFLUX_EMPTY;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_WEBFLUX_NETTY;

import com.seed4j.generator.server.springboot.webflux.web.application.SpringBootWebfluxApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringBootWebfluxModuleConfiguration {

  @Bean
  Seed4JModuleResource springBootWebfluxModule(SpringBootWebfluxApplicationService webflux) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_WEBFLUX_EMPTY)
      .propertiesDefinition(properties())
      .apiDoc("Spring Boot - Webflux", "Empty module: do not use alone. You should add module Spring Boot Webflux Netty")
      .organization(Seed4JModuleOrganization.builder().feature(SPRING_SERVER).addDependency(SPRING_BOOT).build())
      .tags("server", "webflux", "netty")
      .factory(webflux::buildEmptyModule);
  }

  @Bean
  Seed4JModuleResource springBootWebfluxNettyModule(SpringBootWebfluxApplicationService webflux) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_WEBFLUX_NETTY)
      .propertiesDefinition(properties())
      .apiDoc("Spring Boot - Webflux", "Add Spring Boot Webflux Netty")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT_WEBFLUX_EMPTY).build())
      .tags("server", "webflux")
      .factory(webflux::buildNettyModule);
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
