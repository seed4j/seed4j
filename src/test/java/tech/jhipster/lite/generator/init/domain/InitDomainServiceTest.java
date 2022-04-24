package tech.jhipster.lite.generator.init.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPomXml;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class InitDomainServiceTest {

  @Mock
  private NpmService npmService;

  @Mock
  private ProjectRepository projectRepository;

  @InjectMocks
  private InitDomainService initDomainService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    when(npmService.getVersionInCommon(anyString())).thenReturn(Optional.of("0.0.0"));

    assertThatCode(() -> initDomainService.init(project)).doesNotThrowAnyException();
  }

  @Test
  void shouldInitMinimal() {
    Project project = tmpProject();

    assertThatCode(() -> initDomainService.initMinimal(project)).doesNotThrowAnyException();
  }

  @Test
  void shouldAddPackageJson() {
    Project project = tmpProject();
    when(npmService.getVersionInCommon(anyString())).thenReturn(Optional.of("0.0.0"));

    assertThatCode(() -> initDomainService.addPackageJson(project)).doesNotThrowAnyException();

    verify(projectRepository).template(any(Project.class), anyString(), anyString());
    verify(npmService, times(6)).addDevDependency(any(Project.class), anyString(), anyString());
    verify(npmService, times(3)).addScript(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldNotAddPackageJson() {
    Project project = tmpProject();

    assertThatThrownBy(() -> initDomainService.addPackageJson(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddReadme() {
    Project project = tmpProject();

    assertThatCode(() -> initDomainService.addReadme(project)).doesNotThrowAnyException();

    verify(projectRepository).template(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldAddGitConfiguration() {
    Project project = tmpProject();

    assertThatCode(() -> initDomainService.addGitConfiguration(project)).doesNotThrowAnyException();

    verify(projectRepository, times(2)).add(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddEditorConfiguration() {
    Project project = tmpProject();

    assertThatCode(() -> initDomainService.addEditorConfiguration(project)).doesNotThrowAnyException();

    verify(projectRepository).template(any(Project.class), anyString(), anyString());
    verify(projectRepository).add(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldAddPrettier() {
    Project project = tmpProject();

    assertThatCode(() -> initDomainService.addPrettier(project)).doesNotThrowAnyException();

    verify(projectRepository, times(2)).add(any(Project.class), anyString(), anyString());
    verify(projectRepository, times(1)).add(any(Project.class), anyString(), anyString(), anyString());
    verify(projectRepository).template(any(Project.class), anyString(), anyString());
    verify(projectRepository).setExecutable(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldDownload() {
    Project project = tmpProjectWithPomXml();

    when(projectRepository.isJHipsterLiteProject(anyString())).thenReturn(true);

    assertThatCode(() -> initDomainService.download(project)).doesNotThrowAnyException();
  }
}
