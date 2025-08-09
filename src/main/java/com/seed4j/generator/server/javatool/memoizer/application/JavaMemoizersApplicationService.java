package com.seed4j.generator.server.javatool.memoizer.application;

import com.seed4j.generator.server.javatool.memoizer.domain.JavaMemoizersModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JavaMemoizersApplicationService {

  private final JavaMemoizersModuleFactory javaMemoizers;

  public JavaMemoizersApplicationService() {
    javaMemoizers = new JavaMemoizersModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return javaMemoizers.buildModule(properties);
  }
}
