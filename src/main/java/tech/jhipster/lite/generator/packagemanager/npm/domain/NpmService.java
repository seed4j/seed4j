package tech.jhipster.lite.generator.packagemanager.npm.domain;

import java.util.Optional;
import tech.jhipster.lite.generator.project.domain.Project;

public interface NpmService {
  void addDependency(Project project, String dependency, String version);
  void addDevDependency(Project project, String dependency, String version);
  void addScript(Project project, String name, String cmd);

  void install(Project project);
  void prettify(Project project);

  Optional<String> getVersion(String folder, String name);
  Optional<String> getVersionInCommon(String name);
  Optional<String> getVersionInReact(String name);

  Optional<String> getName(String folder);
  Optional<String> getDescription(String folder);
}
