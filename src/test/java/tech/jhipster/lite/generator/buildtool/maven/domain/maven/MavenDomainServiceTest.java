package tech.jhipster.lite.generator.buildtool.maven.domain.maven;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPomXml;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.buildtool.generic.domain.Parent;
import tech.jhipster.lite.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.lite.generator.buildtool.maven.domain.MavenDomainService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class MavenDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @InjectMocks
  MavenDomainService mavenDomainService;

  @Test
  void shouldAddParent() {
    Project project = tmpProjectWithPomXml();
    Parent parent = Parent.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-parent").version("2.5.3").build();

    mavenDomainService.addParent(project, parent);

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddDependency() {
    Project project = tmpProjectWithPomXml();
    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();

    mavenDomainService.addDependency(project, dependency);

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddDependencyWithScopeTest() {
    Project project = tmpProjectWithPomXml();
    Dependency dependency = Dependency
      .builder()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-starter-test")
      .scope("test")
      .build();

    mavenDomainService.addDependency(project, dependency);

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddDependencyWithExclusions() {
    Project project = tmpProjectWithPomXml();
    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-web").build();
    Dependency dependencyToExclude = Dependency
      .builder()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-starter-tomcat")
      .build();

    mavenDomainService.addDependency(project, dependency, List.of(dependencyToExclude));

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldDeleteDependency() {
    Project project = tmpProjectWithPomXml();
    Dependency dependency = Dependency.builder().groupId("org.junit.jupiter").artifactId("junit-jupiter-engine").build();

    mavenDomainService.deleteDependency(project, dependency);

    verify(projectRepository).replaceRegexp(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddPlugin() {
    Project project = tmpProjectWithPomXml();
    Plugin plugin = Plugin.builder().groupId("org.springframework.boot").artifactId("spring-boot-maven-plugin").build();

    mavenDomainService.addPlugin(project, plugin);

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddPluginManagement() {
    Project project = tmpProjectWithPomXml();
    Plugin plugin = Plugin.builder().groupId("org.springframework.boot").artifactId("spring-boot-maven-plugin").build();

    mavenDomainService.addPluginManagement(project, plugin);

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddProperty() {
    Project project = tmpProjectWithPomXml();

    mavenDomainService.addProperty(project, "testcontainers", "1.16.0");

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldDeleteProperty() {
    Project project = tmpProjectWithPomXml();

    mavenDomainService.deleteProperty(project, "java");

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldInit() {
    Project project = tmpProject();

    mavenDomainService.init(project);

    verify(projectRepository).template(any(Project.class), anyString(), anyString());
    verify(projectRepository, times(2)).add(any(Project.class), anyString(), anyString(), anyString());
    verify(projectRepository).add(any(Project.class), anyString(), anyString(), anyString(), anyString());
    verify(projectRepository, times(2)).setExecutable(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldAddPomXml() {
    Project project = tmpProject();

    mavenDomainService.addPomXml(project);

    verify(projectRepository).template(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldAddMavenWrapper() {
    Project project = tmpProject();

    mavenDomainService.addMavenWrapper(project);

    verify(projectRepository, times(2)).add(any(Project.class), anyString(), anyString(), anyString());
    verify(projectRepository).add(any(Project.class), anyString(), anyString(), anyString(), anyString());
    verify(projectRepository, times(2)).setExecutable(any(Project.class), anyString(), anyString());
  }
}
