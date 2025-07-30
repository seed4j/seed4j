package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.resource.JHipsterHiddenModules;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import com.seed4j.module.domain.resource.JHipsterModulesResources;
import java.util.Collection;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JHipsterHiddenResourcesProperties.class)
class JHipsterModulesResourcesConfiguration {

  @Bean
  JHipsterModulesResources jhipsterModulesResources(
    JHipsterHiddenResourcesProperties excludedResources,
    Collection<JHipsterModuleResource> modulesResources
  ) {
    return new JHipsterModulesResources(
      modulesResources,
      new JHipsterHiddenModules(excludedResources.getSlugs(), excludedResources.getTags())
    );
  }
}
