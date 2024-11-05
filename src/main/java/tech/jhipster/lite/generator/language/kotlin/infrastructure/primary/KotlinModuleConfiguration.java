package tech.jhipster.lite.generator.language.kotlin.infrastructure.primary;

import static tech.jhipster.lite.shared.slug.domain.JHLiteFeatureSlug.EXTRA_LANGUAGE;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.GRADLE_JAVA;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.GRADLE_KOTLIN;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.language.kotlin.application.KotlinApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
public class KotlinModuleConfiguration {

  @Bean
  JHipsterModuleResource gradleKotlinLanguageModule(KotlinApplicationService gradle) {
    return JHipsterModuleResource.builder()
      .slug(GRADLE_KOTLIN)
      .withoutProperties()
      .apiDoc("Extra Language", "Add Kotlin Language Supports")
      .organization(JHipsterModuleOrganization.builder().feature(EXTRA_LANGUAGE).addDependency(GRADLE_JAVA).build())
      .tags("buildtool", "test")
      .factory(gradle::buildKotlinLanguageModule);
  }
}
