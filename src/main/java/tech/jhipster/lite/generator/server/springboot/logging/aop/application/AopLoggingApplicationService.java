package tech.jhipster.lite.generator.server.springboot.logging.aop.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.logging.aop.domain.AopLoggingService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class AopLoggingApplicationService {

  private final AopLoggingService aopLoggingService;

  public AopLoggingApplicationService(AopLoggingService aopLoggingService) {
    this.aopLoggingService = aopLoggingService;
  }

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
