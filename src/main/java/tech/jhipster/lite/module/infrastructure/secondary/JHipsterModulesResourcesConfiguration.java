package tech.jhipster.lite.module.infrastructure.secondary;

import java.util.Collection;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.module.domain.resource.JHipsterHiddenModules;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;
import tech.jhipster.lite.module.domain.resource.JHipsterModulesResources;

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
