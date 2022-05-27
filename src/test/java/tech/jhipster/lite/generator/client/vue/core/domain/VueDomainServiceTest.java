package tech.jhipster.lite.generator.client.vue.core.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.project.domain.ProjectFilesAsserter.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class VueDomainServiceTest {

  @Mock
  private ProjectRepository projectRepository;

  @Mock
  private NpmService npmService;

  @InjectMocks
  private VueDomainService vueDomainService;

  @Test
  void shouldAddVue() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    assertThatCode(() -> vueDomainService.addVue(project)).doesNotThrowAnyException();
  }

  @Test
  void shouldNotAddVue() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> vueDomainService.addVue(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotAddVueWhenNoProject() {
    assertThatThrownBy(() -> vueDomainService.addVue(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldNotAddPiniaWhenNoProject() {
    assertThatThrownBy(() -> vueDomainService.addPinia(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldAddDependencies() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    vueDomainService.addDependencies(project);

    verify(npmService).addDependency(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldAddRouterDependencies() {
    Project project = tmpProjectWithPackageJson();
    final String version = "0.0.0";
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of(version));

    vueDomainService.addRouter(project);

    verify(npmService).addDependency(project, "vue-router", version);
  }

  @Test
  void shouldAddRouterFiles() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    vueDomainService.addRouter(project);
    verify(projectRepository, times(2)).template(any(ProjectFile.class));
    verify(projectRepository, times(2)).replaceText(eq(project), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldNotAddDependencies() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> vueDomainService.addDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddDevDependencies() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    vueDomainService.addDevDependencies(project);

    verify(npmService, times(19)).addDevDependency(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldNotAddDevDependencies() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> vueDomainService.addDevDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddScripts() {
    Project project = tmpProjectWithPackageJson();

    vueDomainService.addScripts(project);

    verify(npmService, times(7)).addScript(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldAddViteConfigFiles() {
    Project project = tmpProjectWithPackageJson();

    vueDomainService.addViteConfigFiles(project);

    verify(projectRepository).add(filesCountArgument(4));
  }

  @Test
  void shouldAddRootFiles() {
    Project project = tmpProject();

    vueDomainService.addRootFiles(project);

    verify(projectRepository, times(3)).template(any(ProjectFile.class));
  }

  @Test
  void shouldAddAppFiles() {
    Project project = tmpProject();

    vueDomainService.addAppFiles(project);

    verify(projectRepository, times(3)).template(any(ProjectFile.class));
  }

  @Test
  void shouldAddAppFilesWithCss() {
    Project project = tmpProject();

    vueDomainService.addAppFilesWithCss(project);

    verify(projectRepository, times(2)).template(any(ProjectFile.class));
    verify(projectRepository, times(2)).add(any(ProjectFile.class));
  }

  @Test
  void shouldAddPiniaDependencies() {
    Project project = tmpProject();

    final String version = "1.0.0";
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of(version));

    vueDomainService.addPiniaDependencies(project);
    ArgumentCaptor<String> dependenciesCaptor = ArgumentCaptor.forClass(String.class);
    verify(npmService, times(2)).addDependency(eq(project), dependenciesCaptor.capture(), eq(version));

    assertThat(dependenciesCaptor.getAllValues()).containsAll(Vue.piniaDependencies());
  }

  @Test
  void shouldUpdateMainConfigurationWhenAddingPinia() {
    Project project = tmpProject();
    final String version = "1.0.0";
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of(version));

    vueDomainService.addPinia(project);

    verify(projectRepository, times(5)).replaceText(eq(project), anyString(), eq("main.ts"), anyString(), anyString());
  }

  @Test
  void shouldAddAxiosDependency() {
    Project project = tmpProjectWithPackageJson();
    final String version = "0.0.0";
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of(version));

    vueDomainService.addAxios(project);

    verify(npmService).addDependency(project, "axios", version);
  }

  @Test
  void shouldAddAxiosFile() {
    Project project = tmpProjectWithPackageJson();
    final String version = "0.0.0";
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of(version));

    vueDomainService.addAxios(project);

    verify(projectRepository, times(4)).template(any(ProjectFile.class));
  }

  @Test
  void shouldAddLoggerFiles() {
    Project project = tmpProjectWithPackageJson();

    vueDomainService.addLogger(project);

    verify(projectRepository, times(5)).template(any(ProjectFile.class));
  }
}
