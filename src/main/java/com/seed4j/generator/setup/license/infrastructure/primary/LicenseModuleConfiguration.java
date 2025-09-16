package com.seed4j.generator.setup.license.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.LICENSE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.LICENSE_APACHE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.LICENSE_MIT;

import com.seed4j.generator.setup.license.application.LicenseApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LicenseModuleConfiguration {

  @Bean
  Seed4JModuleResource mitLicenseModule(LicenseApplicationService license) {
    return Seed4JModuleResource.builder()
      .slug(LICENSE_MIT)
      .withoutProperties()
      .apiDoc("License", "Add MIT license file")
      .organization(Seed4JModuleOrganization.builder().feature(LICENSE).build())
      .tags("init", "license")
      .factory(license::buildMitModule);
  }

  @Bean
  Seed4JModuleResource apacheLicenseModule(LicenseApplicationService license) {
    return Seed4JModuleResource.builder()
      .slug(LICENSE_APACHE)
      .withoutProperties()
      .apiDoc("License", "Add APACHE license file")
      .organization(Seed4JModuleOrganization.builder().feature(LICENSE).build())
      .tags("init", "license")
      .factory(license::buildApacheModule);
  }
}
