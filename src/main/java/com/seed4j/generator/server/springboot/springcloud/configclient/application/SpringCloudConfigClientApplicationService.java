package com.seed4j.generator.server.springboot.springcloud.configclient.application;

import com.seed4j.generator.server.springboot.springcloud.configclient.domain.SpringCloudConfigModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringCloudConfigClientApplicationService {

  private final SpringCloudConfigModuleFactory springCloudConfig;

  public SpringCloudConfigClientApplicationService(DockerImages dockerImages) {
    springCloudConfig = new SpringCloudConfigModuleFactory(dockerImages);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return springCloudConfig.buildModule(properties);
  }
}
