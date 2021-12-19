package tech.jhipster.lite.generator.buildtool.maven.application;

import java.util.List;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.buildtool.generic.domain.Parent;
import tech.jhipster.lite.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.lite.generator.buildtool.maven.domain.MavenService;
import tech.jhipster.lite.generator.project.domain.Project;

@Service
public class MavenApplicationService {

  private final MavenService mavenService;

  public MavenApplicationService(MavenService mavenService) {
    this.mavenService = mavenService;
  }

  public void addParent(Project project, Parent parent) {
    mavenService.addParent(project, parent);
  }

  public void addDependency(Project project, Dependency dependency) {
    mavenService.addDependency(project, dependency);
  }

  public void addDependency(Project project, Dependency dependency, List<Dependency> exclusions) {
    mavenService.addDependency(project, dependency, exclusions);
  }

  public void deleteDependency(Project project, Dependency dependency) {
    mavenService.deleteDependency(project, dependency);
  }

  public void addPlugin(Project project, Plugin plugin) {
    mavenService.addPlugin(project, plugin);
  }

  public void addPluginManagement(Project project, Plugin plugin) {
    mavenService.addPluginManagement(project, plugin);
  }

  public void addProperty(Project project, String key, String version) {
    mavenService.addProperty(project, key, version);
  }

  public void deleteProperty(Project project, String key) {
    mavenService.deleteProperty(project, key);
  }

  public void init(Project project) {
    mavenService.init(project);
  }

  public void addPomXml(Project project) {
    mavenService.addPomXml(project);
  }

  public void addMavenWrapper(Project project) {
    mavenService.addMavenWrapper(project);
  }
}
