package com.seed4j.generator.server.springboot.thymeleaf.template;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ALPINE_JS_WEBJARS;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.HTMX_WEBJARS;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_THYMELEAF;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.THYMELEAF_TEMPLATE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.THYMELEAF_TEMPLATE_ALPINEJS_WEBJAR;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.THYMELEAF_TEMPLATE_HTMX_WEBJAR;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.THYMELEAF_TEMPLATE_TAILWINDCSS;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class ThymeleafTemplateModuleResources {

  private static final String GROUP_SPRING_BOOT_THYMELEAF = "Spring Boot - Thymeleaf";
  private static final String TAG_SERVER = "server";
  private static final String TAG_SPRING = "spring";
  private static final String TAG_BOOT = "spring-boot";
  private static final String TAG_THYMELEAF = "thymeleaf";
  private static final String TAG_TAILWINDCSS = "tailwindcss";
  private static final String TAG_WEBJAR = "webjar";

  private ThymeleafTemplateModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Template implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(THYMELEAF_TEMPLATE)
        .propertiesDefinition(
          Seed4JModulePropertiesDefinition.builder()
            .addBasePackage()
            .addProjectBaseName()
            .addSpringConfigurationFormat()
            .addNodePackageManager()
            .build()
        )
        .apiDoc(GROUP_SPRING_BOOT_THYMELEAF, "Add thymeleaf skeleton layout files to the project")
        .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT_THYMELEAF).build())
        .tags(TAG_SERVER, TAG_SPRING, TAG_BOOT, TAG_THYMELEAF)
        .factory(new ThymeleafTemplateModuleFactory()::buildModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Tailwindcss implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(THYMELEAF_TEMPLATE_TAILWINDCSS)
        .propertiesDefinition(
          Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
        )
        .apiDoc(GROUP_SPRING_BOOT_THYMELEAF, "Add tailwindcss to the thymeleaf template")
        .organization(Seed4JModuleOrganization.builder().addDependency(THYMELEAF_TEMPLATE).build())
        .tags(TAG_SERVER, TAG_SPRING, TAG_BOOT, TAG_THYMELEAF, TAG_TAILWINDCSS)
        .factory(new ThymeleafTemplateModuleFactory()::buildTailwindcssModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class HtmxWebjar implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(THYMELEAF_TEMPLATE_HTMX_WEBJAR)
        .propertiesDefinition(
          Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
        )
        .apiDoc(GROUP_SPRING_BOOT_THYMELEAF, "Add htmx webjars scripts to thymeleaf layout")
        .organization(Seed4JModuleOrganization.builder().addDependency(HTMX_WEBJARS).addDependency(THYMELEAF_TEMPLATE).build())
        .tags(TAG_SERVER, TAG_SPRING, TAG_BOOT, TAG_THYMELEAF, TAG_WEBJAR)
        .factory(new ThymeleafTemplateModuleFactory()::buildHtmxWebjarsModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class AlpinejsWebjar implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(THYMELEAF_TEMPLATE_ALPINEJS_WEBJAR)
        .propertiesDefinition(
          Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
        )
        .apiDoc(GROUP_SPRING_BOOT_THYMELEAF, "Add alpine webjars scripts to thymeleaf layout")
        .organization(Seed4JModuleOrganization.builder().addDependency(ALPINE_JS_WEBJARS).addDependency(THYMELEAF_TEMPLATE).build())
        .tags(TAG_SERVER, TAG_SPRING, TAG_BOOT, TAG_THYMELEAF, TAG_WEBJAR)
        .factory(new ThymeleafTemplateModuleFactory()::buildAlpineWebjarsModule);
    }
  }
}
