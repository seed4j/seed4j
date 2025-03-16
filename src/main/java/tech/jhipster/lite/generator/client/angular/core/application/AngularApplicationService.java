package tech.jhipster.lite.generator.client.angular.core.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.angular.core.domain.AngularModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.npm.NpmLazyInstaller;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class AngularApplicationService {

  private final AngularModuleFactory angular;

  public AngularApplicationService(NpmLazyInstaller npmLazyInstaller) {
    angular = new AngularModuleFactory(npmLazyInstaller);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return angular.buildModule(properties);
  }
}
