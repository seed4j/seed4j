package tech.jhipster.lite.generator.server.springboot.core.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface SpringBootService {
  void init(Project project);

  void addSpringBootDependenciesBOM(Project project);
  void addSpringBootDependencies(Project project);
  void addSpringBootMavenPluginManagement(Project project);
  void addSpringBootMavenPlugin(Project project);
  void addMainApp(Project project);
  void addApplicationProperties(Project project);
  void addApplicationLocalProperties(Project project);
  void addApplicationTestProperties(Project project);
  void addLoggingConfiguration(Project project);
  void addLoggingTestConfiguration(Project project);
}
