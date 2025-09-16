package com.seed4j.generator.server.springboot.springcloud.configclient.application;

import com.seed4j.generator.server.springboot.springcloud.configclient.domain.SpringCloudConfigModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringCloudConfigClientApplicationService {

  private final SpringCloudConfigModuleFactory springCloudConfig;

  public SpringCloudConfigClientApplicationService(DockerImages dockerImages) {
    springCloudConfig = new SpringCloudConfigModuleFactory(dockerImages);
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return springCloudConfig.buildModule(properties);
  }
}
