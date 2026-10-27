package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.resource.Seed4JHiddenModules;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;
import com.seed4j.module.domain.resource.Seed4JModulesResources;
import java.util.Collection;
import java.util.ServiceLoader;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(Seed4JHiddenResourcesProperties.class)
class Seed4JModulesResourcesConfiguration {

  @Bean
  Seed4JModulesResources seed4JModulesResources(
    Seed4JHiddenResourcesProperties excludedResources,
    Collection<Seed4JModuleResource> modulesResources,
    Seed4JModuleServices services
  ) {
    return seed4JModulesResources(excludedResources, modulesResources, ServiceLoader.load(Seed4JModuleResourceProvider.class), services);
  }

  Seed4JModulesResources seed4JModulesResources(
    Seed4JHiddenResourcesProperties excludedResources,
    Collection<Seed4JModuleResource> modulesResources,
    Iterable<Seed4JModuleResourceProvider> providers,
    Seed4JModuleServices services
  ) {
    Collection<Seed4JModuleResource> allResources = Stream.concat(
      modulesResources.stream(),
      StreamSupport.stream(providers.spliterator(), false).map(provider -> provider.get(services))
    ).toList();

    return new Seed4JModulesResources(allResources, new Seed4JHiddenModules(excludedResources.getSlugs(), excludedResources.getTags()));
  }
}
