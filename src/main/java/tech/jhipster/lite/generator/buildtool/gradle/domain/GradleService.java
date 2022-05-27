package tech.jhipster.lite.generator.buildtool.gradle.domain;

import java.util.Optional;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.buildtool.generic.domain.Repository;
import tech.jhipster.lite.generator.project.domain.Project;

public interface GradleService {
  void initJava(Project project);

  void addDependency(Project project, Dependency dependency);
  void deleteDependency(Project project, Dependency dependency);
  void addRepository(Project project, Repository repository);

  void addJavaBuildGradleKts(Project project);
  void addGradleWrapper(Project project);

  Optional<String> getGroup(String folder);
}
