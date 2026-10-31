package com.seed4j.engine;

import static com.seed4j.module.domain.resource.Seed4JModulesResourceFixture.defaultModuleResource;

import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public class TestModuleResourceProvider implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return defaultModuleResource();
  }
}
