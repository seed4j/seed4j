package com.seed4j.generator.setup.license.application;

import com.seed4j.generator.setup.license.domain.LicenseModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class LicenseApplicationService {

  private final LicenseModuleFactory license;

  public LicenseApplicationService() {
    this.license = new LicenseModuleFactory();
  }

  public SeedModule buildMitModule(SeedModuleProperties properties) {
    return license.buildMitModule(properties);
  }

  public SeedModule buildApacheModule(SeedModuleProperties properties) {
    return license.buildApacheModule(properties);
  }
}
