package tech.jhipster.lite.generator.server.springboot.banner.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteModuleSlug.BANNER;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.SPRING_BOOT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.banner.application.BannerApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class BannerModuleConfiguration {

  private static final String GROUP = "Spring Boot - Banner";
  private static final String SERVER = "server";
  private static final String SPRING = "spring";
  private static final String SPRING_BOOT_TAG = "spring-boot";

  @Bean
  JHipsterModuleResource bannerModule(BannerApplicationService banners) {
    return JHipsterModuleResource
      .builder()
      .slug(BANNER)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addProjectName().build())
      .apiDoc(GROUP, "Add Spring Boot banner with project name")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_BOOT).build())
      .tags(SERVER, SPRING, SPRING_BOOT_TAG)
      .factory(banners::buildModule);
  }
}
