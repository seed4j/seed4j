package tech.jhipster.lite.generator.buildtool.maven.domain.maven;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.buildtool.maven.domain.Maven.*;
import static tech.jhipster.lite.generator.project.domain.Constants.POM_XML;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.buildtool.generic.domain.Parent;
import tech.jhipster.lite.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.lite.generator.buildtool.generic.domain.Repository;
import tech.jhipster.lite.generator.buildtool.maven.domain.MavenDomainService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class MavenDomainServiceTest {

  @Mock
  private ProjectRepository projectRepository;

  @InjectMocks
  private MavenDomainService mavenDomainService;

  @Test
  void shouldAddParent() {
    Project project = tmpProjectWithPomXml();
    Parent parent = Parent.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-parent").version("0.0.0").build();

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

    mavenDomainService.addProperty(project, "testcontainers", "0.0.0");

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldDeleteProperty() {
    Project project = tmpProjectWithPomXml();

    mavenDomainService.deleteProperty(project, "java");

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddRepository() {
    Project project = tmpProjectWithPomXml();
    Repository repository = Repository.builder().id("spring-milestone").url("https://repo.spring.io/milestone").build();

    mavenDomainService.addRepository(project, repository);

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddPluginRepository() {
    Project project = tmpProjectWithPomXml();
    Repository repository = Repository.builder().id("spring-milestone").url("https://repo.spring.io/milestone").build();

    mavenDomainService.addPluginRepository(project, repository);

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldInitJava() {
    Project project = tmpProject();

    mavenDomainService.initJava(project);

    verify(projectRepository).template(any(ProjectFile.class));
    verify(projectRepository, times(4)).add(any(ProjectFile.class));
    verify(projectRepository, times(2)).setExecutable(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldAddJavaPomXml() {
    Project project = tmpProject();

    mavenDomainService.addJavaPomXml(project);

    verify(projectRepository).template(any(ProjectFile.class));
  }

  @Test
  void shouldAddMavenWrapper() {
    Project project = tmpProject();

    mavenDomainService.addMavenWrapper(project);

    verify(projectRepository, times(4)).add(any(ProjectFile.class));
    verify(projectRepository, times(2)).setExecutable(any(Project.class), anyString(), anyString());
  }

  @Nested
  class GetGroupIdTest {

    @Test
    void shouldGetGroupId() {
      Project project = tmpProjectWithPomXml();

      try (MockedStatic<FileUtils> fileUtilsMock = Mockito.mockStatic(FileUtils.class)) {
        mavenDomainService.getGroupId(project.getFolder());
        fileUtilsMock.verify(
          () -> FileUtils.getValueBetween(getPath(project.getFolder(), POM_XML), GROUP_ID_BEGIN, GROUP_ID_END),
          times(1)
        );
      }
    }

    @Test
    void shouldNotGetGroupIdForNullAndBlank() {
      assertThatThrownBy(() -> mavenDomainService.getGroupId(null))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining("folder");
      assertThatThrownBy(() -> mavenDomainService.getGroupId(" "))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining("folder");
    }
  }

  @Nested
  class GetNameTest {

    @Test
    void shouldGetName() {
      Project project = tmpProjectWithPomXml();

      try (MockedStatic<FileUtils> fileUtilsMock = Mockito.mockStatic(FileUtils.class)) {
        mavenDomainService.getName(project.getFolder());
        fileUtilsMock.verify(() -> FileUtils.getValueBetween(getPath(project.getFolder(), POM_XML), NAME_BEGIN, NAME_END), times(1));
      }
    }

    @Test
    void shouldNotGetNameForNullAndBlank() {
      assertThatThrownBy(() -> mavenDomainService.getName(null))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining("folder");
      assertThatThrownBy(() -> mavenDomainService.getName(" "))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining("folder");
    }
  }

  @Nested
  class GetVersionTest {

    @Test
    void shouldGetVersion() {
      assertThat(mavenDomainService.getVersion("spring-boot")).isNotEmpty();
    }

    @Test
    void shouldNotGetVersionForNull() {
      assertThatThrownBy(() -> mavenDomainService.getVersion(null))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining("name");
    }

    @Test
    void shouldNotGetVersionForBlank() {
      assertThatThrownBy(() -> mavenDomainService.getVersion(" "))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining("name");
    }

    @Test
    void shouldNotGetVersion() {
      assertThat(mavenDomainService.getVersion("unknown")).isEmpty();
    }

    @Test
    void shouldNotGetVersionForWrongPattern() {
      try (MockedStatic<FileUtils> fileUtilsMock = Mockito.mockStatic(FileUtils.class)) {
        fileUtilsMock.when(() -> FileUtils.getPath(Mockito.anyString())).thenReturn("mypath");
        fileUtilsMock
          .when(() -> FileUtils.readLineInClasspath(Mockito.any(), Mockito.anyString()))
          .thenReturn(Optional.of(" <spring-boot.version>0.0.0</spring-boot>"));

        assertThat(mavenDomainService.getVersion("spring-boot")).isEmpty();
      }
    }
  }
}
