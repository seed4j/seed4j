package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.resource.SeedHiddenModules;
import com.seed4j.module.domain.resource.SeedModuleResource;
import com.seed4j.module.domain.resource.SeedModulesResources;
import java.util.Collection;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SeedHiddenResourcesProperties.class)
class SeedModulesResourcesConfiguration {

  @Bean
  SeedModulesResources seedModulesResources(
    SeedHiddenResourcesProperties excludedResources,
    Collection<SeedModuleResource> modulesResources
  ) {
    return new SeedModulesResources(modulesResources, new SeedHiddenModules(excludedResources.getSlugs(), excludedResources.getTags()));
  }
}
