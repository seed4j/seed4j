package tech.jhipster.lite.generator.server.javatool.base.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.javatool.base.domain.JavaBaseModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class JavaBaseApplicationService {

  private final JavaBaseModuleFactory javaBase;

  public JavaBaseApplicationService() {
    javaBase = new JavaBaseModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return javaBase.buildModule(properties);
  }
}
