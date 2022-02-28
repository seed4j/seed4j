package tech.jhipster.lite.generator.server.springboot.docker.domain;

import tech.jhipster.lite.generator.project.domain.Project;

public interface SpringBootDockerService {
  void addJib(Project project);
  void addJibFiles(Project project);
  void addJibPlugin(Project project);
  void addDockerFile(Project project);
}
