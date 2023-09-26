package tech.jhipster.lite.generator.server.springboot.logsspy.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.logsspy.domain.LogsSpyModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class LogsSpyApplicationService {

  private final LogsSpyModuleFactory factory;

  public LogsSpyApplicationService() {
    factory = new LogsSpyModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
