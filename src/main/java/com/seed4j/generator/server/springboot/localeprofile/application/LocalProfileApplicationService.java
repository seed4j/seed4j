package com.seed4j.generator.server.springboot.localeprofile.application;

import com.seed4j.generator.server.springboot.localeprofile.domain.LocalProfileModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class LocalProfileApplicationService {

  private final LocalProfileModuleFactory localProfile;

  public LocalProfileApplicationService() {
    localProfile = new LocalProfileModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return localProfile.buildModule(properties);
  }
}
