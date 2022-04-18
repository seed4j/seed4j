package tech.jhipster.lite.generator.buildtool.gradle.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.buildtool.gradle.domain.GradleService;
import tech.jhipster.lite.generator.project.domain.Project;

@Service
public record GradleApplicationService(
  GradleService gradleService) {

  public void init(Project project) {
    gradleService.initJava(project);
  }

  public void addBuildGradle(Project project) {
    gradleService.addJavaBuildGradleKts(project);
  }

  public void addGradleWrapper(Project project) {
    gradleService.addGradleWrapper(project);
  }
}
