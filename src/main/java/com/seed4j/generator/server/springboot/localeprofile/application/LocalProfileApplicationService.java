package com.seed4j.generator.server.springboot.localeprofile.application;

import com.seed4j.generator.server.springboot.localeprofile.domain.LocalProfileModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class LocalProfileApplicationService {

  private final LocalProfileModuleFactory localProfile;

  public LocalProfileApplicationService() {
    localProfile = new LocalProfileModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return localProfile.buildModule(properties);
  }
}
