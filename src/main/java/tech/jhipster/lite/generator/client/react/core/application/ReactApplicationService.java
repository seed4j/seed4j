package tech.jhipster.lite.generator.client.react.core.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.react.core.domain.ReactModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.npm.NpmLazyInstaller;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class ReactApplicationService {

  private final ReactModuleFactory react;

  public ReactApplicationService(NpmLazyInstaller npmLazyInstaller) {
    react = new ReactModuleFactory(npmLazyInstaller);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return react.buildModule(properties);
  }
}
