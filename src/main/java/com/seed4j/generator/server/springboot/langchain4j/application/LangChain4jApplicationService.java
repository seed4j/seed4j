package com.seed4j.generator.server.springboot.langchain4j.application;

import com.seed4j.generator.server.springboot.langchain4j.domain.LangChain4jModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

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
