package tech.jhipster.lite.generator.buildtool.maven.domain;

import java.util.List;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.buildtool.generic.domain.Parent;
import tech.jhipster.lite.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.lite.generator.project.domain.Project;

public interface MavenService {
  void addParent(Project project, Parent parent);
  void addDependency(Project project, Dependency dependency);
  void deleteDependency(Project project, Dependency dependency);
  void addDependency(Project project, Dependency dependency, List<Dependency> exclusions);
  void addPlugin(Project project, Plugin plugin);
  void addPluginManagement(Project project, Plugin plugin);
  void addProperty(Project project, String key, String version);
  void deleteProperty(Project project, String key);

  void init(Project project);

  void addPomXml(Project project);
  void addMavenWrapper(Project project);
}
