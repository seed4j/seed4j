package com.seed4j.generator.server.springboot.thymeleaf.template.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.ALPINE_JS_WEBJARS;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.HTMX_WEBJARS;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_THYMELEAF;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.THYMELEAF_TEMPLATE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.THYMELEAF_TEMPLATE_ALPINEJS_WEBJAR;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.THYMELEAF_TEMPLATE_HTMX_WEBJAR;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.THYMELEAF_TEMPLATE_TAILWINDCSS;

import com.seed4j.generator.server.springboot.thymeleaf.template.application.ThymeleafTemplateModuleApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ThymeleafTemplateModuleConfiguration {

  private static final String GROUP_SPRING_BOOT_THYMELEAF = "Spring Boot - Thymeleaf";
  private static final String TAG_SERVER = "server";
  private static final String TAG_SPRING = "spring";
  private static final String TAG_BOOT = "spring-boot";
  private static final String TAG_THYMELEAF = "thymeleaf";
  private static final String TAG_TAILWINDCSS = "tailwindcss";
  private static final String TAG_WEBJAR = "webjar";

  @Bean
  SeedModuleResource thymeleafTemplateModule(ThymeleafTemplateModuleApplicationService thymeleafTemplate) {
    return SeedModuleResource.builder()
      .slug(THYMELEAF_TEMPLATE)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addSpringConfigurationFormat()
          .addNodePackageManager()
          .build()
      )
      .apiDoc(GROUP_SPRING_BOOT_THYMELEAF, "Add thymeleaf skeleton layout files to the project")
      .organization(SeedModuleOrganization.builder().addDependency(SPRING_BOOT_THYMELEAF).build())
      .tags(TAG_SERVER, TAG_SPRING, TAG_BOOT, TAG_THYMELEAF)
      .factory(thymeleafTemplate::buildThymeleafTemplateModule);
  }

  @Bean
  SeedModuleResource thymeleafTemplateTailwindcssModule(ThymeleafTemplateModuleApplicationService thymeleafTemplate) {
    return SeedModuleResource.builder()
      .slug(THYMELEAF_TEMPLATE_TAILWINDCSS)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc(GROUP_SPRING_BOOT_THYMELEAF, "Add tailwindcss to the thymeleaf template")
      .organization(SeedModuleOrganization.builder().addDependency(THYMELEAF_TEMPLATE).build())
      .tags(TAG_SERVER, TAG_SPRING, TAG_BOOT, TAG_THYMELEAF, TAG_TAILWINDCSS)
      .factory(thymeleafTemplate::buildThymeleafTemplateTailwindcssModule);
  }

  @Bean
  SeedModuleResource thymeleafTemplateHtmxWebjarsModule(ThymeleafTemplateModuleApplicationService thymeleafTemplate) {
    return SeedModuleResource.builder()
      .slug(THYMELEAF_TEMPLATE_HTMX_WEBJAR)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc(GROUP_SPRING_BOOT_THYMELEAF, "Add htmx webjars scripts to thymeleaf layout")
      .organization(SeedModuleOrganization.builder().addDependency(HTMX_WEBJARS).addDependency(THYMELEAF_TEMPLATE).build())
      .tags(TAG_SERVER, TAG_SPRING, TAG_BOOT, TAG_THYMELEAF, TAG_WEBJAR)
      .factory(thymeleafTemplate::buildThymeleafHtmxWebjarsModule);
  }

  @Bean
  SeedModuleResource thymeleafTemplateAlpineWebjarsModule(ThymeleafTemplateModuleApplicationService thymeleafTemplate) {
    return SeedModuleResource.builder()
      .slug(THYMELEAF_TEMPLATE_ALPINEJS_WEBJAR)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc(GROUP_SPRING_BOOT_THYMELEAF, "Add alpine webjars scripts to thymeleaf layout")
      .organization(SeedModuleOrganization.builder().addDependency(ALPINE_JS_WEBJARS).addDependency(THYMELEAF_TEMPLATE).build())
      .tags(TAG_SERVER, TAG_SPRING, TAG_BOOT, TAG_THYMELEAF, TAG_WEBJAR)
      .factory(thymeleafTemplate::buildThymeleafAlpinejsWebjarsModule);
  }
}
