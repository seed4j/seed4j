package com.seed4j.generator.server.springboot.localeprofile.application;

import com.seed4j.generator.server.springboot.localeprofile.domain.LocalProfileModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class LocalProfileApplicationService {

  private final LocalProfileModuleFactory localProfile;

  public LocalProfileApplicationService() {
    localProfile = new LocalProfileModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return localProfile.buildModule(properties);
  }
}
