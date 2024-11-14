package tech.jhipster.lite.generator.server.springboot.mvc.sample.langchain4j.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.sample.langchain4j.domain.SampleLangChain4jModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SampleLangChain4jApplicationService {

  private final SampleLangChain4jModuleFactory factory;

  public SampleLangChain4jApplicationService() {
    factory = new SampleLangChain4jModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
