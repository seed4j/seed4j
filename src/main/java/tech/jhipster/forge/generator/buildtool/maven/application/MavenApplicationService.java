package tech.jhipster.forge.generator.buildtool.maven.application;

import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import tech.jhipster.forge.generator.buildtool.maven.domain.MavenService;
import tech.jhipster.forge.generator.project.domain.BuildToolType;
import tech.jhipster.forge.generator.project.domain.Dependency;
import tech.jhipster.forge.generator.project.domain.Parent;
import tech.jhipster.forge.generator.project.domain.Plugin;
import tech.jhipster.forge.generator.project.domain.Project;
import tech.jhipster.forge.generator.project.domain.added.BuildToolAdded;

@Service
public class MavenApplicationService {

  private final MavenService mavenService;
  private final ApplicationEventPublisher publisher;

  public MavenApplicationService(MavenService mavenService, ApplicationEventPublisher publisher) {
    this.mavenService = mavenService;
    this.publisher = publisher;
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

  public void addPlugin(Project project, Plugin plugin) {
    mavenService.addPlugin(project, plugin);
  }

  public void addProperty(Project project, String key, String version) {
    mavenService.addProperty(project, key, version);
  }

  public void init(Project project) {
    BuildToolAdded event = project.addBuildTool(BuildToolType.MAVEN);

    publisher.publishEvent(event);
  }

  public void addPomXml(Project project) {
    mavenService.addPomXml(project);
  }

  public void addMavenWrapper(Project project) {
    mavenService.addMavenWrapper(project);
  }
}
