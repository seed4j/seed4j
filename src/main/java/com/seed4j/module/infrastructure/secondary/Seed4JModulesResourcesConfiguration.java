package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.resource.Seed4JHiddenModules;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModulesResources;
import java.util.Collection;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(Seed4JHiddenResourcesProperties.class)
class Seed4JModulesResourcesConfiguration {

  @Bean
  Seed4JModulesResources seed4JModulesResources(
    Seed4JHiddenResourcesProperties excludedResources,
    Collection<Seed4JModuleResource> modulesResources
  ) {
    return new Seed4JModulesResources(modulesResources, new Seed4JHiddenModules(excludedResources.getSlugs(), excludedResources.getTags()));
  }
}
