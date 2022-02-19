package tech.jhipster.lite.generator.client.vite.vue.core.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.tmpProject;
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
class ViteVueDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @Mock
  NpmService npmService;

  @InjectMocks
  private ViteVueDomainService viteVueDomainService;

  @Test
  void shouldAddViteVue() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    assertThatCode(() -> viteVueDomainService.addViteVue(project)).doesNotThrowAnyException();
  }

  @Test
  void shouldNotAddViteVue() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> viteVueDomainService.addViteVue(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddStyledViteVue() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    assertThatCode(() -> viteVueDomainService.addStyledViteVue(project)).doesNotThrowAnyException();
  }

  @Test
  void shouldNotAddStyledIndex() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> viteVueDomainService.addStyledViteVue(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddDependencies() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    viteVueDomainService.addDependencies(project);

    verify(npmService, times(1)).addDependency(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldNotAddDependencies() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> viteVueDomainService.addDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddDevDependencies() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    viteVueDomainService.addDevDependencies(project);

    verify(npmService, times(17)).addDevDependency(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldNotAddDevDependencies() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> viteVueDomainService.addDevDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddScripts() {
    Project project = tmpProjectWithPackageJson();

    viteVueDomainService.addScripts(project);

    verify(npmService, times(4)).addScript(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldAddViteConfigFiles() {
    Project project = tmpProjectWithPackageJson();

    viteVueDomainService.addViteConfigFiles(project);

    verify(projectRepository, times(4)).add(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldAddRootFiles() {
    Project project = tmpProject();

    viteVueDomainService.addRootFiles(project);

    verify(projectRepository, times(3)).template(any(Project.class), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddAppFiles() {
    Project project = tmpProject();

    viteVueDomainService.addAppFiles(project);

    verify(projectRepository, times(3)).template(any(Project.class), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddAppFilesWithoutCss() {
    Project project = tmpProject();

    viteVueDomainService.addAppFilesWithoutCss(project);

    verify(projectRepository, times(2)).template(any(Project.class), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddAppFilesWithCss() {
    Project project = tmpProject();

    viteVueDomainService.addAppFilesWithCss(project);

    verify(projectRepository, times(2)).template(any(Project.class), anyString(), anyString(), anyString(), anyString());
    verify(projectRepository, times(2)).add(any(Project.class), anyString(), anyString(), anyString());
  }
}
