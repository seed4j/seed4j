package tech.jhipster.lite.generator.buildtool.generic.domain;

import java.util.List;
import java.util.Optional;
import tech.jhipster.lite.generator.project.domain.BuildToolType;
import tech.jhipster.lite.generator.project.domain.Project;

public interface BuildToolService {
  void addParent(Project project, Parent parent);
  void addDependency(Project project, Dependency dependency);
  void addDependency(Project project, Dependency dependency, List<Dependency> exclusions);
  void addDependencyManagement(Project project, Dependency dependency);
  void addPlugin(Project project, Plugin plugin);
  void addPluginManagement(Project project, Plugin plugin);
  void addProperty(Project project, String key, String value);
  void addRepository(Project project, Repository repository);
  void addPluginRepository(Project project, Repository repository);
  void deleteDependency(Project project, Dependency dependency);

  void init(Project project, BuildToolType buildTool);

  Optional<String> getVersion(Project project, String name);

  Optional<String> getGroup(Project project);

  Optional<String> getName(Project project);
}
