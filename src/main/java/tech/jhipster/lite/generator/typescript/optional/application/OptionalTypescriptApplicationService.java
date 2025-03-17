package tech.jhipster.lite.generator.typescript.optional.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.typescript.optional.domain.OptionalTypescriptModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class OptionalTypescriptApplicationService {

  private final OptionalTypescriptModuleFactory optionalTypescript;

  public OptionalTypescriptApplicationService() {
    this.optionalTypescript = new OptionalTypescriptModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties project) {
    return optionalTypescript.buildModule(project);
  }
}
