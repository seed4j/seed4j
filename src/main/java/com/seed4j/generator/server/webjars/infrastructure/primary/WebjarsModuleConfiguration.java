package com.seed4j.generator.server.webjars.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.ALPINE_JS_WEBJARS;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.HTMX_WEBJARS;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRING_BOOT_THYMELEAF;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.WEBJARS_LOCATOR;

import com.seed4j.generator.server.webjars.application.WebjarsApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class WebjarsModuleConfiguration {

  private static final String SERVER_TAG = "server";
  private static final String WEB_TAG = "web";
  private static final String WEBJARS_GROUP = "WebJars";

  @Bean
  SeedModuleResource webjarsLocatorModule(WebjarsApplicationService webjars) {
    return SeedModuleResource.builder()
      .slug(WEBJARS_LOCATOR)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc(WEBJARS_GROUP, "Add webjars locator to the project")
      .organization(SeedModuleOrganization.builder().addDependency(SPRING_BOOT_THYMELEAF).build())
      .tags(SERVER_TAG, WEB_TAG)
      .factory(webjars::buildWebjarsLocatorModule);
  }

  @Bean
  SeedModuleResource webjarsHtmxModule(WebjarsApplicationService webjars) {
    return SeedModuleResource.builder()
      .slug(HTMX_WEBJARS)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc(WEBJARS_GROUP, "Add HTMX webjar to the project")
      .organization(SeedModuleOrganization.builder().addDependency(WEBJARS_LOCATOR).build())
      .tags(SERVER_TAG, WEB_TAG)
      .factory(webjars::buildWebjarsHtmxModule);
  }

  @Bean
  SeedModuleResource webjarsAlpineJSModule(WebjarsApplicationService webjars) {
    return SeedModuleResource.builder()
      .slug(ALPINE_JS_WEBJARS)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc(WEBJARS_GROUP, "Add alpine.js webjar to the project")
      .organization(SeedModuleOrganization.builder().addDependency(WEBJARS_LOCATOR).build())
      .tags(SERVER_TAG, WEB_TAG)
      .factory(webjars::buildWebjarsAlpineJSModule);
  }
}
