package tech.jhipster.lite.generator.server.springboot.springcloud.configclient.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.springcloud.configclient.domain.SpringCloudConfigModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

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
