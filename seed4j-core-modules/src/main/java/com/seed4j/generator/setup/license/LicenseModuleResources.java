package com.seed4j.generator.setup.license;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.LICENSE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.LICENSE_APACHE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.LICENSE_MIT;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class LicenseModuleResources {

  private LicenseModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Mit implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(LICENSE_MIT)
        .withoutProperties()
        .apiDoc("License", "Add MIT license file")
        .organization(Seed4JModuleOrganization.builder().feature(LICENSE).build())
        .tags("init", "license")
        .factory(new LicenseModuleFactory()::buildMitModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Apache implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(LICENSE_APACHE)
        .withoutProperties()
        .apiDoc("License", "Add APACHE license file")
        .organization(Seed4JModuleOrganization.builder().feature(LICENSE).build())
        .tags("init", "license")
        .factory(new LicenseModuleFactory()::buildApacheModule);
    }
  }
}
