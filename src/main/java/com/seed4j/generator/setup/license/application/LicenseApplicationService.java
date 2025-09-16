package com.seed4j.generator.setup.license.application;

import com.seed4j.generator.setup.license.domain.LicenseModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class LicenseApplicationService {

  private final LicenseModuleFactory license;

  public LicenseApplicationService() {
    this.license = new LicenseModuleFactory();
  }

  public Seed4JModule buildMitModule(Seed4JModuleProperties properties) {
    return license.buildMitModule(properties);
  }

  public Seed4JModule buildApacheModule(Seed4JModuleProperties properties) {
    return license.buildApacheModule(properties);
  }
}
