package tech.jhipster.lite.generator.server.springboot.database.mssql.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.generator.server.springboot.database.mssql.domain.MsSQLModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class MsSQLApplicationService {

  private final MsSQLModuleFactory factory;

  public MsSQLApplicationService(DockerImages dockerImages) {
    factory = new MsSQLModuleFactory(dockerImages);
  }

  public JHipsterModule build(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
