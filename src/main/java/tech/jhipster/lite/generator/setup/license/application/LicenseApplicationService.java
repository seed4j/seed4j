package tech.jhipster.lite.generator.setup.license.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.setup.license.domain.LicenseModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

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
