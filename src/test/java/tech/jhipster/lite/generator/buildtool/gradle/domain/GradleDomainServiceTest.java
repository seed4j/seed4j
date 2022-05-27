package tech.jhipster.lite.generator.buildtool.gradle.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.common.domain.WordUtils.DQ;
import static tech.jhipster.lite.generator.buildtool.gradle.domain.Gradle.*;
import static tech.jhipster.lite.generator.project.domain.Constants.BUILD_GRADLE_KTS;
import static tech.jhipster.lite.generator.project.domain.ProjectFilesAsserter.*;

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
import tech.jhipster.lite.generator.buildtool.generic.domain.Repository;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class GradleDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @InjectMocks
  GradleDomainService gradleDomainService;

  @Test
  void shouldInitJava() {
    Project project = tmpProject();

    gradleDomainService.initJava(project);

    verify(projectRepository, times(2)).template(any(ProjectFile.class));
    verify(projectRepository).add(filesCountArgument(4));
    verify(projectRepository, times(2)).setExecutable(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldAddJavaBuildGradleKts() {
    Project project = tmpProject();

    gradleDomainService.addJavaBuildGradleKts(project);

    verify(projectRepository, times(2)).template(any(ProjectFile.class));
  }

  @Test
  void shouldAddGradleWrapper() {
    Project project = tmpProject();

    gradleDomainService.addGradleWrapper(project);

    verify(projectRepository).add(filesCountArgument(4));
    verify(projectRepository, times(2)).setExecutable(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldAddDependency() {
    Project project = tmpProject();
    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();

    gradleDomainService.addDependency(project, dependency);

    String newTextExpected = ("implementation(\"org.springframework.boot:spring-boot-starter\")\n" + GRADLE_NEEDLE_DEPENDENCY).indent(2);
    verify(projectRepository).replaceText(project, "", "build.gradle.kts", REGEXP_SPACE_STAR + GRADLE_NEEDLE_DEPENDENCY, newTextExpected);
  }

  @Test
  void shouldDeleteDependency() {
    Project project = tmpProjectWithBuildGradle();
    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();

    gradleDomainService.deleteDependency(project, dependency);

    String regexToReplace = "(?s)" + "implementation(\"org.springframework.boot:spring-boot-starter.*\")\n".indent(2);
    verify(projectRepository).replaceRegexp(project, "", "build.gradle.kts", regexToReplace, "");
  }

  @Test
  void shouldAddRepository() {
    Project project = tmpProjectWithBuildGradle();
    Repository repository = Repository.builder().id("spring-milestone").url("https://repo.spring.io/milestone").build();

    gradleDomainService.addRepository(project, repository);

    String repositoryString =
      """
        maven {
          url = uri("https://repo.spring.io/milestone")
        }
        // jhipster-needle-gradle-repository
      """;
    verify(projectRepository).replaceText(project, "", "build.gradle.kts", REGEXP_SPACE_STAR + GRADLE_NEEDLE_REPOSITORY, repositoryString);
  }

  @Test
  void shouldGetGroup() {
    Project project = tmpProjectWithBuildGradle();

    try (MockedStatic<FileUtils> fileUtilsMock = Mockito.mockStatic(FileUtils.class)) {
      gradleDomainService.getGroup(project.getFolder());
      fileUtilsMock.verify(() -> FileUtils.getValueBetween(getPath(project.getFolder(), BUILD_GRADLE_KTS), "group = " + DQ, DQ), times(1));
    }
  }

  @Test
  void shouldNotGetGroupForNullAndBlank() {
    assertThatThrownBy(() -> gradleDomainService.getGroup(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("folder");
    assertThatThrownBy(() -> gradleDomainService.getGroup(" "))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("folder");
  }
}
