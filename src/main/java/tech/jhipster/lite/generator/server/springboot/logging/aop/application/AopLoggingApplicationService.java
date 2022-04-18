package tech.jhipster.lite.generator.server.springboot.logging.aop.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.logging.aop.domain.AopLoggingService;

@Service
public record AopLoggingApplicationService(AopLoggingService aopLoggingService) {
  public void init(Project project) {
    aopLoggingService.init(project);
  }

  public void addDialectJava(Project project) {
    aopLoggingService.addJavaFiles(project);
  }

  public void addMavenDependencies(Project project) {
    aopLoggingService.addMavenDependencies(project);
  }

  public void addProperties(Project project) {
    aopLoggingService.addProperties(project);
  }
}
