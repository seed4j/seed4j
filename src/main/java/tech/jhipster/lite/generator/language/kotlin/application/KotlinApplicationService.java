package tech.jhipster.lite.generator.language.kotlin.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.language.kotlin.domain.KotlinModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class KotlinApplicationService {

  private final KotlinModuleFactory factory;

  public KotlinApplicationService() {
    factory = new KotlinModuleFactory();
  }

  public JHipsterModule buildKotlinLanguageModule(JHipsterModuleProperties properties) {
    return factory.buildKotlinLanguageModule(properties);
  }
}
