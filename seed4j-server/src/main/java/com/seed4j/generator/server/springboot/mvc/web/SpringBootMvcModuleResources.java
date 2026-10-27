package com.seed4j.generator.server.springboot.mvc.web;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_MVC_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.LOGS_SPY;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_MVC_EMPTY;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_TOMCAT;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class SpringBootMvcModuleResources {

  private static final String SPRING_BOOT_MVC_API_GROUP = "Spring Boot - MVC";
  private static final String SERVER_TAG = "server";
  private static final String SPRING_BOOT_TAG = "spring-boot";
  private static final String SPRING_TAG = "spring";
  private static final String MVC_TAG = "mvc";
  private static final String WEB_TAG = "web";

  private SpringBootMvcModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class MvcEmpty implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(SPRING_BOOT_MVC_EMPTY)
        .propertiesDefinition(properties())
        .apiDoc(SPRING_BOOT_MVC_API_GROUP, "Empty module: do not use alone. You should add another module in Spring MVC Server")
        .organization(Seed4JModuleOrganization.builder().feature(SPRING_SERVER).addDependency(SPRING_BOOT).build())
        .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, MVC_TAG, WEB_TAG)
        .factory(new SpringBootMvcModuleFactory()::buildEmptyModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Tomcat implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(SPRING_BOOT_TOMCAT)
        .propertiesDefinition(properties())
        .apiDoc(SPRING_BOOT_MVC_API_GROUP, "Add Spring Boot MVC with Tomcat")
        .organization(mvcServerOrganization())
        .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, MVC_TAG, WEB_TAG, "tomcat")
        .factory(new SpringBootMvcModuleFactory()::buildTomcatModule);
    }
  }

  private static Seed4JModulePropertiesDefinition properties() {
    return Seed4JModulePropertiesDefinition.builder()
      .addBasePackage()
      .addIndentation()
      .addServerPort()
      .addSpringConfigurationFormat()
      .build();
  }

  private static Seed4JModuleOrganization mvcServerOrganization() {
    return Seed4JModuleOrganization.builder()
      .feature(SPRING_MVC_SERVER)
      .addDependency(SPRING_BOOT_MVC_EMPTY)
      .addDependency(LOGS_SPY)
      .build();
  }
}
