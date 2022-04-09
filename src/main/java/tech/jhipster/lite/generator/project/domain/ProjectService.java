package tech.jhipster.lite.generator.project.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface ProjectService {
  void init(Project project);

  void addPackageJson(Project project);
  void addReadme(Project project);
  void addGitConfiguration(Project project);
  void addEditorConfiguration(Project project);
  void addPrettier(Project project);
  void gitInit(Project project);
  byte[] download(Project project);
}
