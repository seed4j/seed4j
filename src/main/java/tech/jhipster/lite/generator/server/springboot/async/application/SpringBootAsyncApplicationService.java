package tech.jhipster.lite.generator.server.springboot.async.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.async.domain.SpringBootAsyncService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class SpringBootAsyncApplicationService {

  private final SpringBootAsyncService springBootAsyncService;

  public SpringBootAsyncApplicationService(SpringBootAsyncService springBootAsyncService) {
    this.springBootAsyncService = springBootAsyncService;
  }

  public void init(Project project) {
    springBootAsyncService.init(project);
  }

  public void addJavaFiles(Project project) {
    springBootAsyncService.addJavaFiles(project);
  }

  public void addProperties(Project project) {
    springBootAsyncService.addProperties(project);
  }
}
