package tech.jhipster.lite.generator.server.springboot.async.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.async.domain.SpringBootAsyncService;

@Service
public record SpringBootAsyncApplicationService(SpringBootAsyncService springBootAsyncService) {
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
