package com.seed4j.generator.server.javatool.memoizer.application;

import com.seed4j.generator.server.javatool.memoizer.domain.JavaMemoizersModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JavaMemoizersApplicationService {

  private final JavaMemoizersModuleFactory javaMemoizers;

  public JavaMemoizersApplicationService() {
    javaMemoizers = new JavaMemoizersModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return javaMemoizers.buildModule(properties);
  }
}
