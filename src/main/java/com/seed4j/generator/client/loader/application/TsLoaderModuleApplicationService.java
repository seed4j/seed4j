package com.seed4j.generator.client.loader.application;

import com.seed4j.generator.client.loader.domain.TsLoaderModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class TsLoaderModuleApplicationService {

  private final TsLoaderModuleFactory tsLoader;

  public TsLoaderModuleApplicationService() {
    this.tsLoader = new TsLoaderModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return tsLoader.buildModule(properties);
  }
}
