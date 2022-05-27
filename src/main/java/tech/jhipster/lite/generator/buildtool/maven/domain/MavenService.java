package tech.jhipster.lite.generator.buildtool.maven.domain;

import java.util.List;
import java.util.Optional;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.buildtool.generic.domain.Parent;
import tech.jhipster.lite.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.lite.generator.buildtool.generic.domain.Repository;
import tech.jhipster.lite.generator.project.domain.Project;

public interface MavenService {
  void addParent(Project project, Parent parent);
  void addDependency(Project project, Dependency dependency);
  void addDependency(Project project, Dependency dependency, List<Dependency> exclusions);
  void addDependencyManagement(Project project, Dependency dependency);
  void deleteDependency(Project project, Dependency dependency);
  void addPlugin(Project project, Plugin plugin);
  void addPluginManagement(Project project, Plugin plugin);
  void addProperty(Project project, String key, String value);
  void deleteProperty(Project project, String key);
  void addRepository(Project project, Repository repository);
  void addPluginRepository(Project project, Repository repository);

  void initJava(Project project);

  void addJavaPomXml(Project project);
  void addMavenWrapper(Project project);

  Optional<String> getVersion(String name);

  Optional<String> getGroupId(String folder);

  Optional<String> getName(String folder);
}
