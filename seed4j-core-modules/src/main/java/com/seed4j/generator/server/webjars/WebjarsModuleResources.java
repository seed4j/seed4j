package com.seed4j.generator.server.webjars;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ALPINE_JS_WEBJARS;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.HTMX_WEBJARS;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_THYMELEAF;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.WEBJARS_LOCATOR;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class WebjarsModuleResources {

  private static final String SERVER_TAG = "server";
  private static final String WEB_TAG = "web";
  private static final String WEBJARS_GROUP = "WebJars";

  private WebjarsModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class WebjarsLocator implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(WEBJARS_LOCATOR)
        .propertiesDefinition(
          Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
        )
        .apiDoc(WEBJARS_GROUP, "Add webjars locator to the project")
        .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT_THYMELEAF).build())
        .tags(SERVER_TAG, WEB_TAG)
        .factory(new WebjarsModuleFactory()::buildWebjarsLocatorModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class HtmxWebjars implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(HTMX_WEBJARS)
        .propertiesDefinition(
          Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
        )
        .apiDoc(WEBJARS_GROUP, "Add HTMX webjar to the project")
        .organization(Seed4JModuleOrganization.builder().addDependency(WEBJARS_LOCATOR).build())
        .tags(SERVER_TAG, WEB_TAG)
        .factory(new WebjarsModuleFactory()::buildWebjarsHtmxModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class AlpineJsWebjars implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(ALPINE_JS_WEBJARS)
        .propertiesDefinition(
          Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
        )
        .apiDoc(WEBJARS_GROUP, "Add alpine.js webjar to the project")
        .organization(Seed4JModuleOrganization.builder().addDependency(WEBJARS_LOCATOR).build())
        .tags(SERVER_TAG, WEB_TAG)
        .factory(new WebjarsModuleFactory()::buildWebjarsAlpineJSModule);
    }
  }
}
