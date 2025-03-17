package tech.jhipster.lite.generator.server.springboot.langchain4j.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.langchain4j.domain.LangChain4jModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class LangChain4jApplicationService {

  private final LangChain4jModuleFactory langChain4j;

  public LangChain4jApplicationService() {
    langChain4j = new LangChain4jModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return langChain4j.buildModule(properties);
  }
}
