package tech.jhipster.lite.generator.server.springboot.logging.logstash.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.logging.logstash.domain.LogstashService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class LogstashApplicationService {

  private final LogstashService logstashService;

  public LogstashApplicationService(LogstashService logstashService) {
    this.logstashService = logstashService;
  }

  public void init(Project project) {
    logstashService.init(project);
  }

  public void addDependencies(Project project) {
    logstashService.addDependencies(project);
  }

  public void addJavaFiles(Project project) {
    logstashService.addJavaFiles(project);
  }

  public void addProperties(Project project) {
    logstashService.addProperties(project);
  }

  public void addLoggerInConfiguration(Project project) {
    logstashService.addLoggerInConfiguration(project);
  }
}
