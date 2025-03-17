package tech.jhipster.lite.generator.buildtool.gradle.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.buildtool.gradle.domain.GradleModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class GradleApplicationService {

  private final GradleModuleFactory gradle;

  public GradleApplicationService() {
    gradle = new GradleModuleFactory();
  }

  public JHipsterModule buildGradleModule(JHipsterModuleProperties properties) {
    return gradle.buildGradleModule(properties);
  }

  public JHipsterModule buildGradleWrapperModule(JHipsterModuleProperties properties) {
    return gradle.buildGradleWrapperModule(properties);
  }
}
