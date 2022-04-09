package tech.jhipster.lite.generator.client.vue.core.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPackageJson;

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
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class VueDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @Mock
  NpmService npmService;

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
  void shouldAddStyledVue() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    assertThatCode(() -> vueDomainService.addStyledVue(project)).doesNotThrowAnyException();
  }

  @Test
  void shouldNotAddStyledIndex() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> vueDomainService.addStyledVue(project)).isExactlyInstanceOf(GeneratorException.class);
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
    final String version = "0.0.0";
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of(version));

    vueDomainService.addRouter(project);
    ArgumentCaptor<String> filesCaptor = ArgumentCaptor.forClass(String.class);
    verify(projectRepository, times(2)).template(eq(project), any(String.class), filesCaptor.capture(), anyString());
    verify(projectRepository, times(2)).replaceText(eq(project), anyString(), anyString(), anyString(), anyString());

    assertThat(filesCaptor.getAllValues()).contains("router.ts", "Router.spec.ts");
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

    verify(npmService, times(17)).addDevDependency(any(Project.class), anyString(), anyString());
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

    verify(projectRepository, times(4)).add(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldAddRootFiles() {
    Project project = tmpProject();

    vueDomainService.addRootFiles(project);

    verify(projectRepository, times(3)).template(any(Project.class), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddAppFiles() {
    Project project = tmpProject();

    vueDomainService.addAppFiles(project);

    verify(projectRepository, times(3)).template(any(Project.class), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddAppFilesWithoutCss() {
    Project project = tmpProject();

    vueDomainService.addAppFilesWithoutCss(project);

    verify(projectRepository, times(2)).template(any(Project.class), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddAppFilesWithCss() {
    Project project = tmpProject();

    vueDomainService.addAppFilesWithCss(project);

    verify(projectRepository, times(2)).template(any(Project.class), anyString(), anyString(), anyString(), anyString());
    verify(projectRepository, times(2)).add(any(Project.class), anyString(), anyString(), anyString());
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
}
