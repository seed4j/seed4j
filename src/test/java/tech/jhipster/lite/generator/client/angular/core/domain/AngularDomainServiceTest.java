package tech.jhipster.lite.generator.client.angular.core.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.*;

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
  void shouldInit() {
    Project project = tmpProject();
    when(npmService.getVersionInAngular(anyString())).thenReturn(Optional.of("0.0.0"));

    angularDomainService.init(project);

    verify(npmService, times(11)).addDevDependency(any(Project.class), anyString(), anyString());
    verify(npmService, times(11)).addDependency(any(Project.class), anyString(), anyString());
    verify(npmService, times(5)).addScript(any(Project.class), anyString(), anyString());

    verify(projectRepository, times(5)).add(any(Project.class), anyString(), anyString());
    verify(projectRepository, times(15)).template(any(Project.class), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddDependencies() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersionInAngular(anyString())).thenReturn(Optional.of("0.0.0"));

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
    when(npmService.getVersionInAngular(anyString())).thenReturn(Optional.of("0.0.0"));

    angularDomainService.addDevDependencies(project);

    verify(npmService, times(11)).addDevDependency(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldNotAddDevDependencies() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> angularDomainService.addDevDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
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
  void shouldAddAngularFiles() {
    Project project = tmpProjectWithPackageJson();

    angularDomainService.addAngularFiles(project);

    verify(projectRepository, times(15)).template(any(Project.class), anyString(), anyString(), anyString());
  }
}
