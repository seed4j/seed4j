package tech.jhipster.lite.generator.server.springboot.mvc.sample.flyway.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.sample.flyway.domain.SampleFlywayModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SampleFlywayApplicationService {

  private final SampleFlywayModuleFactory sampleFlyway;

  public SampleFlywayApplicationService() {
    sampleFlyway = new SampleFlywayModuleFactory();
  }

  public JHipsterModule buildPostgresqlModule(JHipsterModuleProperties properties) {
    return sampleFlyway.buildPostgresqlModule(properties);
  }

  public JHipsterModule buildNotPostgresqlModule(JHipsterModuleProperties properties) {
    return sampleFlyway.buildNotPostgresqlModule(properties);
  }
}
