package tech.jhipster.lite.generator.buildtool.generic.domain;

import static tech.jhipster.lite.generator.project.domain.BuildToolType.MAVEN;

import java.util.List;
import java.util.Optional;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.gradle.domain.GradleService;
import tech.jhipster.lite.generator.buildtool.maven.domain.MavenService;
import tech.jhipster.lite.generator.project.domain.BuildToolType;
import tech.jhipster.lite.generator.project.domain.Project;

public class BuildToolDomainService implements BuildToolService {

  public static final String EXCEPTION_NO_BUILD_TOOL = "No build tool";
  private final MavenService mavenService;
  private final GradleService gradleService;

  public BuildToolDomainService(MavenService mavenService, GradleService gradleService) {
    this.mavenService = mavenService;
    this.gradleService = gradleService;
  }

  @Override
  public void addParent(Project project, Parent parent) {
    if (project.isMavenProject()) {
      mavenService.addParent(project, parent);
    } else {
      throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
    }
  }

  @Override
  public void addDependency(Project project, Dependency dependency) {
    if (project.isMavenProject()) {
      mavenService.addDependency(project, dependency);
    } else if (project.isGradleProject()) {
      gradleService.addDependency(project, dependency);
    } else {
      throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
    }
  }

  @Override
  public void addDependency(Project project, Dependency dependency, List<Dependency> exclusions) {
    if (project.isMavenProject()) {
      mavenService.addDependency(project, dependency, exclusions);
    } else {
      throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
    }
  }

  @Override
  public void addDependencyManagement(Project project, Dependency dependency) {
    if (project.isMavenProject()) {
      mavenService.addDependencyManagement(project, dependency);
    } else {
      throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
    }
  }

  @Override
  public void addPlugin(Project project, Plugin plugin) {
    if (project.isMavenProject()) {
      mavenService.addPlugin(project, plugin);
    } else {
      throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
    }
  }

  @Override
  public void addPluginManagement(Project project, Plugin plugin) {
    if (project.isMavenProject()) {
      mavenService.addPluginManagement(project, plugin);
    } else {
      throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
    }
  }

  @Override
  public void addProperty(Project project, String key, String value) {
    if (project.isMavenProject()) {
      mavenService.addProperty(project, key, value);
    } else {
      throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
    }
  }

  @Override
  public void addRepository(Project project, Repository repository) {
    if (project.isMavenProject()) {
      mavenService.addRepository(project, repository);
    } else if (project.isGradleProject()) {
      gradleService.addRepository(project, repository);
    } else {
      throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
    }
  }

  @Override
  public void addPluginRepository(Project project, Repository repository) {
    if (project.isMavenProject()) {
      mavenService.addPluginRepository(project, repository);
    } else {
      throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
    }
  }

  @Override
  public void deleteDependency(Project project, Dependency dependency) {
    if (project.isMavenProject()) {
      mavenService.deleteDependency(project, dependency);
    } else if (project.isGradleProject()) {
      gradleService.deleteDependency(project, dependency);
    } else {
      throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
    }
  }

  @Override
  public void init(Project project, BuildToolType buildTool) {
    if (buildTool == MAVEN) {
      mavenService.initJava(project);
    } else {
      throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
    }
  }

  @Override
  public Optional<String> getVersion(Project project, String name) {
    if (project.isMavenProject()) {
      return mavenService.getVersion(name);
    }
    throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
  }

  @Override
  public Optional<String> getGroup(Project project) {
    if (project.isMavenProject()) {
      return mavenService.getGroupId(project.getFolder());
    } else if (project.isGradleProject()) {
      return gradleService.getGroup(project.getFolder());
    }
    throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
  }

  @Override
  public Optional<String> getName(Project project) {
    if (project.isMavenProject()) {
      return mavenService.getName(project.getFolder());
    }
    throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
  }
}
