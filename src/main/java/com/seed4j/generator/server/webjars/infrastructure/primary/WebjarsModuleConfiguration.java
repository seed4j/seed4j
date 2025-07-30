package com.seed4j.generator.server.webjars.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.ALPINE_JS_WEBJARS;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.HTMX_WEBJARS;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_THYMELEAF;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.WEBJARS_LOCATOR;

import com.seed4j.generator.server.webjars.application.WebjarsApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class WebjarsModuleConfiguration {

  private static final String SERVER_TAG = "server";
  private static final String WEB_TAG = "web";
  private static final String WEBJARS_GROUP = "WebJars";

  @Bean
  JHipsterModuleResource webjarsLocatorModule(WebjarsApplicationService webjars) {
    return JHipsterModuleResource.builder()
      .slug(WEBJARS_LOCATOR)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc(WEBJARS_GROUP, "Add webjars locator to the project")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_BOOT_THYMELEAF).build())
      .tags(SERVER_TAG, WEB_TAG)
      .factory(webjars::buildWebjarsLocatorModule);
  }

  @Bean
  JHipsterModuleResource webjarsHtmxModule(WebjarsApplicationService webjars) {
    return JHipsterModuleResource.builder()
      .slug(HTMX_WEBJARS)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc(WEBJARS_GROUP, "Add HTMX webjar to the project")
      .organization(JHipsterModuleOrganization.builder().addDependency(WEBJARS_LOCATOR).build())
      .tags(SERVER_TAG, WEB_TAG)
      .factory(webjars::buildWebjarsHtmxModule);
  }

  @Bean
  JHipsterModuleResource webjarsAlpineJSModule(WebjarsApplicationService webjars) {
    return JHipsterModuleResource.builder()
      .slug(ALPINE_JS_WEBJARS)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc(WEBJARS_GROUP, "Add alpine.js webjar to the project")
      .organization(JHipsterModuleOrganization.builder().addDependency(WEBJARS_LOCATOR).build())
      .tags(SERVER_TAG, WEB_TAG)
      .factory(webjars::buildWebjarsAlpineJSModule);
  }
}
