package tech.jhipster.lite.generator.packagemanager.npm.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface NpmRepository {
  void npmInstall(Project project);
  void npmPrettierFormat(Project project);
}
