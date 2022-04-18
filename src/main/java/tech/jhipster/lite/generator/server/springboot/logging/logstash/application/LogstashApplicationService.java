package tech.jhipster.lite.generator.server.springboot.logging.logstash.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.logging.logstash.domain.LogstashService;

@Service
public record LogstashApplicationService(LogstashService logstashService) {
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
