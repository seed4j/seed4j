package com.seed4j.generator.client.loader.application;

import com.seed4j.generator.client.loader.domain.TsLoaderModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class TsLoaderModuleApplicationService {

  private final TsLoaderModuleFactory tsLoader;

  public TsLoaderModuleApplicationService() {
    this.tsLoader = new TsLoaderModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return tsLoader.buildModule(properties);
  }
}
