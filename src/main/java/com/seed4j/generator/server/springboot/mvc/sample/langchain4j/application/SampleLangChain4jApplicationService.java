package com.seed4j.generator.server.springboot.mvc.sample.langchain4j.application;

import com.seed4j.generator.server.springboot.mvc.sample.langchain4j.domain.SampleLangChain4jModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SampleLangChain4jApplicationService {

  private final SampleLangChain4jModuleFactory sampleLangChain4j;

  public SampleLangChain4jApplicationService() {
    sampleLangChain4j = new SampleLangChain4jModuleFactory();
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return sampleLangChain4j.buildModule(properties);
  }
}
