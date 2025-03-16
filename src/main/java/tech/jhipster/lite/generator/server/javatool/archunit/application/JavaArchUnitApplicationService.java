package tech.jhipster.lite.generator.server.javatool.archunit.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.javatool.archunit.domain.ArchUnitModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class JavaArchUnitApplicationService {

  private final ArchUnitModuleFactory archUnit;

  public JavaArchUnitApplicationService() {
    archUnit = new ArchUnitModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return archUnit.buildModule(properties);
  }
}
