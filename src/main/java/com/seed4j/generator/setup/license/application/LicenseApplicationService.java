package com.seed4j.generator.setup.license.application;

import com.seed4j.generator.setup.license.domain.LicenseModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class LicenseApplicationService {

  private final LicenseModuleFactory license;

  public LicenseApplicationService() {
    this.license = new LicenseModuleFactory();
  }

  public JHipsterModule buildMitModule(JHipsterModuleProperties properties) {
    return license.buildMitModule(properties);
  }

  public JHipsterModule buildApacheModule(JHipsterModuleProperties properties) {
    return license.buildApacheModule(properties);
  }
}
