package com.seed4j.generator.server.javatool.nullaway.application;

import com.seed4j.generator.server.javatool.nullaway.domain.NullAwayModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class NullAwayApplicationService {

  private final NullAwayModuleFactory nullAway;

  public NullAwayApplicationService() {
    nullAway = new NullAwayModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return nullAway.buildModule(properties);
  }
}
