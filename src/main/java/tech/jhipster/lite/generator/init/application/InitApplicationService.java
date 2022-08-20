package tech.jhipster.lite.generator.init.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.init.domain.InitModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.npm.domain.NpmVersions;

@Service
public class InitApplicationService {

  private final InitModuleFactory factory;

  public InitApplicationService(NpmVersions npmVersions) {
    factory = new InitModuleFactory(npmVersions);
  }

  public JHipsterModule buildFullInitModule(JHipsterModuleProperties properties) {
    return factory.buildFullModule(properties);
  }

  public JHipsterModule buildMinimalInitModule(JHipsterModuleProperties properties) {
    return factory.buildMinimalModule(properties);
  }
}
