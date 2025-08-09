package com.seed4j.generator.client.loader.application;

import com.seed4j.generator.client.loader.domain.TsLoaderModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class TsLoaderModuleApplicationService {

  private final TsLoaderModuleFactory tsLoader;

  public TsLoaderModuleApplicationService() {
    this.tsLoader = new TsLoaderModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return tsLoader.buildModule(properties);
  }
}
