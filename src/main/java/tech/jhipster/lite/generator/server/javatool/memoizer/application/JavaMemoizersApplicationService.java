package tech.jhipster.lite.generator.server.javatool.memoizer.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.javatool.memoizer.domain.JavaMemoizersModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class JavaMemoizersApplicationService {

  private final JavaMemoizersModuleFactory javaMemoizers;

  public JavaMemoizersApplicationService() {
    javaMemoizers = new JavaMemoizersModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return javaMemoizers.buildModule(properties);
  }
}
