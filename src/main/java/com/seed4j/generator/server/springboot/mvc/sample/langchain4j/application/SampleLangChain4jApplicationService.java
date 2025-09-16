package com.seed4j.generator.server.springboot.mvc.sample.langchain4j.application;

import com.seed4j.generator.server.springboot.mvc.sample.langchain4j.domain.SampleLangChain4jModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SampleLangChain4jApplicationService {

  private final SampleLangChain4jModuleFactory sampleLangChain4j;

  public SampleLangChain4jApplicationService() {
    sampleLangChain4j = new SampleLangChain4jModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return sampleLangChain4j.buildModule(properties);
  }
}
