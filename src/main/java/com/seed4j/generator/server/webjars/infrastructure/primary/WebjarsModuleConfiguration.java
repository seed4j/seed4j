package com.seed4j.generator.server.webjars.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ALPINE_JS_WEBJARS;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.HTMX_WEBJARS;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_THYMELEAF;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.WEBJARS_LOCATOR;

import com.seed4j.generator.server.webjars.application.WebjarsApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class WebjarsModuleConfiguration {

  private static final String SERVER_TAG = "server";
  private static final String WEB_TAG = "web";
  private static final String WEBJARS_GROUP = "WebJars";

  @Bean
  Seed4JModuleResource webjarsLocatorModule(WebjarsApplicationService webjars) {
    return Seed4JModuleResource.builder()
      .slug(WEBJARS_LOCATOR)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc(WEBJARS_GROUP, "Add webjars locator to the project")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT_THYMELEAF).build())
      .tags(SERVER_TAG, WEB_TAG)
      .factory(webjars::buildWebjarsLocatorModule);
  }

  @Bean
  Seed4JModuleResource webjarsHtmxModule(WebjarsApplicationService webjars) {
    return Seed4JModuleResource.builder()
      .slug(HTMX_WEBJARS)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc(WEBJARS_GROUP, "Add HTMX webjar to the project")
      .organization(Seed4JModuleOrganization.builder().addDependency(WEBJARS_LOCATOR).build())
      .tags(SERVER_TAG, WEB_TAG)
      .factory(webjars::buildWebjarsHtmxModule);
  }

  @Bean
  Seed4JModuleResource webjarsAlpineJSModule(WebjarsApplicationService webjars) {
    return Seed4JModuleResource.builder()
      .slug(ALPINE_JS_WEBJARS)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc(WEBJARS_GROUP, "Add alpine.js webjar to the project")
      .organization(Seed4JModuleOrganization.builder().addDependency(WEBJARS_LOCATOR).build())
      .tags(SERVER_TAG, WEB_TAG)
      .factory(webjars::buildWebjarsAlpineJSModule);
  }
}
