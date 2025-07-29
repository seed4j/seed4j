package com.seed4j.generator.setup.license.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.LICENSE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.LICENSE_APACHE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.LICENSE_MIT;

import com.seed4j.generator.setup.license.application.LicenseApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LicenseModuleConfiguration {

  @Bean
  JHipsterModuleResource mitLicenseModule(LicenseApplicationService license) {
    return JHipsterModuleResource.builder()
      .slug(LICENSE_MIT)
      .withoutProperties()
      .apiDoc("License", "Add MIT license file")
      .organization(JHipsterModuleOrganization.builder().feature(LICENSE).build())
      .tags("init", "license")
      .factory(license::buildMitModule);
  }

  @Bean
  JHipsterModuleResource apacheLicenseModule(LicenseApplicationService license) {
    return JHipsterModuleResource.builder()
      .slug(LICENSE_APACHE)
      .withoutProperties()
      .apiDoc("License", "Add APACHE license file")
      .organization(JHipsterModuleOrganization.builder().feature(LICENSE).build())
      .tags("init", "license")
      .factory(license::buildApacheModule);
  }
}
