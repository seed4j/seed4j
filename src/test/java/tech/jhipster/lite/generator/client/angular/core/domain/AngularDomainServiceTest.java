package tech.jhipster.lite.generator.client.angular.core.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPackageJson;

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
class AngularDomainServiceTest {

  @InjectMocks
  AngularDomainService angularDomainService;

  @Mock
  NpmService npmService;

  @Mock
  ProjectRepository projectRepository;

  @Test
  void shouldAddAngular() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    assertThatCode(() -> angularDomainService.addAngular(project)).doesNotThrowAnyException();
  }

  @Test
  void shouldNotAddAngular() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> angularDomainService.addAngular(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddJwtAngular() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    assertThatCode(() -> angularDomainService.addJwtAngular(project)).doesNotThrowAnyException();
  }

  @Test
  void shouldNotAddJwtAngular() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> angularDomainService.addJwtAngular(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddDependencies() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    angularDomainService.addDependencies(project);

    verify(npmService, times(11)).addDependency(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldNotAddDependencies() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> angularDomainService.addDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddDevDependencies() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    angularDomainService.addDevDependencies(project);

    verify(npmService, times(11)).addDevDependency(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldNotAddDevDependencies() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> angularDomainService.addDevDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddJwtDependencies() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    angularDomainService.addJwtDependencies(project);

    verify(npmService, times(1)).addDependency(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldNotAddJwtDependencies() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> angularDomainService.addJwtDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddScripts() {
    Project project = tmpProjectWithPackageJson();

    angularDomainService.addScripts(project);

    verify(npmService, times(5)).addScript(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldAddFiles() {
    Project project = tmpProjectWithPackageJson();

    angularDomainService.addFiles(project);

    verify(projectRepository, times(5)).add(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldAddJwtFiles() {
    Project project = tmpProjectWithPackageJson();

    angularDomainService.addJwtFiles(project);

    verify(projectRepository, times(1)).template(any(Project.class), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddAngularFiles() {
    Project project = tmpProjectWithPackageJson();

    angularDomainService.addAngularFiles(project);

    verify(projectRepository, times(15)).template(any(Project.class), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddAngularJwtFiles() {
    Project project = tmpProjectWithPackageJson();

    angularDomainService.addAngularJwtFiles(project);

    verify(projectRepository, times(9)).template(any(Project.class), anyString(), anyString(), anyString());
  }
}
