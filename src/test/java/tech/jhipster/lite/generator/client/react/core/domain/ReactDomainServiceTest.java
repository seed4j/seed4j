package tech.jhipster.lite.generator.client.react.core.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.tmpProject;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class ReactDomainServiceTest {

  @InjectMocks
  ReactDomainService reactDomainService;

  @Mock
  NpmService npmService;

  @Mock
  ProjectRepository projectRepository;

  @Test
  void shouldAddReact() {
    Project project = tmpProject();
    when(npmService.getVersionInReact(anyString())).thenReturn(Optional.of("0.0.0"));

    reactDomainService.addReact(project);

    verify(npmService, times(2)).addDependency(any(Project.class), anyString(), anyString());
    verify(npmService, times(16)).addDevDependency(any(Project.class), anyString(), anyString());
    verify(npmService, times(6)).addScript(any(Project.class), anyString(), anyString());

    verify(projectRepository, times(3)).add(any(Project.class), anyString(), anyString());
    verify(projectRepository, times(8)).template(any(Project.class), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddStyledReact() {
    Project project = tmpProject();
    when(npmService.getVersionInReact(anyString())).thenReturn(Optional.of("0.0.0"));

    reactDomainService.addStyledReact(project);

    verify(npmService, times(2)).addDependency(any(Project.class), anyString(), anyString());
    verify(npmService, times(16)).addDevDependency(any(Project.class), anyString(), anyString());
    verify(npmService, times(6)).addScript(any(Project.class), anyString(), anyString());

    verify(projectRepository, times(3)).add(any(Project.class), anyString(), anyString());
    verify(projectRepository, times(8)).template(any(Project.class), anyString(), anyString(), anyString());
  }

  @Test
  void shouldNotAddReact() {
    Project project = tmpProject();

    assertThatThrownBy(() -> reactDomainService.addReact(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotAddDependencies() {
    Project project = tmpProject();

    assertThatThrownBy(() -> reactDomainService.addDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotAddDevDependencies() {
    Project project = tmpProject();

    assertThatThrownBy(() -> reactDomainService.addDevDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }
}
