package tech.jhipster.lite.generator.typescript.core.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.typescript.core.domain.TypescriptModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.npm.NpmLazyInstaller;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class TypescriptApplicationService {

  private final TypescriptModuleFactory typescript;

  public TypescriptApplicationService(NpmLazyInstaller npmLazyInstaller) {
    this.typescript = new TypescriptModuleFactory(npmLazyInstaller);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties project) {
    return typescript.buildModule(project);
  }
}
