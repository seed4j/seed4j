package tech.jhipster.lite.generator.init.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.init.domain.InitService;
import tech.jhipster.lite.generator.project.domain.Project;

@Service
public record InitApplicationService(InitService initService) {
  public void init(Project project) {
    initService.init(project);
  }

  public void initMinimal(Project project) {
    initService.initMinimal(project);
  }

  public void addPackageJson(Project project) {
    initService.addPackageJson(project);
  }

  public void addReadme(Project project) {
    initService.addReadme(project);
  }

  public void addGitConfiguration(Project project) {
    initService.addGitConfiguration(project);
  }

  public void addEditorConfiguration(Project project) {
    initService.addEditorConfiguration(project);
  }

  public void addPrettier(Project project) {
    initService.addPrettier(project);
  }

  public void gitInit(Project project) {
    initService.gitInit(project);
  }

  public byte[] download(Project project) {
    return initService.download(project);
  }
}
