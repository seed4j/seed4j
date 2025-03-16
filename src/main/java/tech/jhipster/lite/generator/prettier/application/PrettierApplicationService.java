package tech.jhipster.lite.generator.prettier.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.prettier.domain.PrettierModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.npm.NpmLazyInstaller;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class PrettierApplicationService {

  private final PrettierModuleFactory prettier;

  public PrettierApplicationService(NpmLazyInstaller npmLazyInstaller) {
    this.prettier = new PrettierModuleFactory(npmLazyInstaller);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return prettier.buildModule(properties);
  }
}
