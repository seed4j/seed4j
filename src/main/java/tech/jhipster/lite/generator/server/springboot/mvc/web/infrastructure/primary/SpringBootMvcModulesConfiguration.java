package tech.jhipster.lite.generator.server.springboot.mvc.web.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class SpringBootMvcModulesConfiguration {

  private static final String SPRING_BOOT_MVC_API_GROUP = "Spring Boot - MVC";
  private static final String SERVER_TAG = "server";
  private static final String SPRING_BOOT_TAG = "spring-boot";
  private static final String SPRING_TAG = "spring";
  private static final String MVC_TAG = "mvc";
  private static final String WEB_TAG = "web";

  @Bean
  JHipsterModuleResource springBootMvcModule(SpringBootMvcApplicationService springBootMvc) {
    return JHipsterModuleResource
      .builder()
      .slug(SPRING_BOOT_MVC_EMPTY)
      .propertiesDefinition(properties())
      .apiDoc(SPRING_BOOT_MVC_API_GROUP, "Empty module: do not use alone. You should add another module in Spring MVC Server")
      .organization(JHipsterModuleOrganization.builder().feature(SPRING_SERVER).addDependency(SPRING_BOOT).build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, MVC_TAG, WEB_TAG)
      .factory(springBootMvc::buildEmptyModule);
  }

  @Bean
  JHipsterModuleResource springBootTomcatMvcModule(SpringBootMvcApplicationService springBootMvc) {
    return JHipsterModuleResource
      .builder()
      .slug(SPRING_BOOT_TOMCAT)
      .propertiesDefinition(properties())
      .apiDoc(SPRING_BOOT_MVC_API_GROUP, "Add Spring Boot MVC with Tomcat")
      .organization(mvcServerOrganization())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, MVC_TAG, WEB_TAG, "tomcat")
      .factory(springBootMvc::buildTomcatModule);
  }

  @Bean
  JHipsterModuleResource springBootUndertowMvcModule(SpringBootMvcApplicationService springBootMvc) {
    return JHipsterModuleResource
      .builder()
      .slug(SPRING_BOOT_UNDERTOW)
      .propertiesDefinition(properties())
      .apiDoc(SPRING_BOOT_MVC_API_GROUP, "Add Spring Boot MVC with Undertow")
      .organization(mvcServerOrganization())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, MVC_TAG, WEB_TAG, "undertow")
      .factory(springBootMvc::buildUndertowModule);
  }

  private JHipsterModulePropertiesDefinition properties() {
    return JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addServerPort().build();
  }

  private JHipsterModuleOrganization mvcServerOrganization() {
    return JHipsterModuleOrganization.builder().feature(SPRING_MVC_SERVER).addDependency(SPRING_BOOT_MVC_EMPTY).build();
  }
}
