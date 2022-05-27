package tech.jhipster.lite.generator.project.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

@UnitTest
@ExtendWith(MockitoExtension.class)
class ProjectDomainServiceTest {

  @Mock
  NpmService npmService;

  @Mock
  BuildToolService buildToolService;

  @Mock
  SpringBootCommonService springBootCommonService;

  @InjectMocks
  ProjectDomainService projectDomainService;

  @Test
  void shouldGetProjectDetailsForInitProject() {
    Project project = tmpProject();
    when(this.npmService.getDescription(project.getFolder())).thenReturn(Optional.of("Cool Project"));
    when(this.springBootCommonService.getProperty(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());
    Project projectResponse = projectDomainService.getProjectDetails(project.getFolder());

    assertThat(projectResponse.getConfig(PROJECT_NAME)).contains("Cool Project");
    verify(this.buildToolService, never()).getGroup(Mockito.any());
    verify(this.buildToolService, never()).getName(Mockito.any());
  }

  @Test
  void shouldGetProjectDetailsForMavenProject() {
    Project project = tmpProjectWithPomXml();
    when(this.npmService.getDescription(project.getFolder())).thenReturn(Optional.of("Cool Project"));
    when(this.buildToolService.getName(Mockito.any())).thenReturn(Optional.of("coolProject"));
    when(this.buildToolService.getGroup(Mockito.any())).thenReturn(Optional.of("com.cool.project"));
    when(this.springBootCommonService.getProperty(Mockito.any(), Mockito.any())).thenReturn(Optional.of("8084"));
    Project projectResponse = projectDomainService.getProjectDetails(project.getFolder());

    assertThat(projectResponse.getConfig(PROJECT_NAME)).contains("Cool Project");
    assertThat(projectResponse.getConfig(BASE_NAME)).contains("coolProject");
    assertThat(projectResponse.getConfig(PACKAGE_NAME)).contains("com.cool.project");
    assertThat(projectResponse.getConfig("serverPort")).contains(8084);

    verify(this.buildToolService, times(1)).getGroup(Mockito.any());
    verify(this.buildToolService, times(1)).getName(Mockito.any());
  }

  @Test
  void shouldGetProjectDetailsForMavenMvcProject() {
    Project project = tmpProjectWithBuildGradle();
    when(this.npmService.getDescription(project.getFolder())).thenReturn(Optional.of("Cool Project"));
    when(this.buildToolService.getGroup(Mockito.any())).thenReturn(Optional.of("com.cool.project"));
    when(this.springBootCommonService.getProperty(Mockito.any(), Mockito.any())).thenReturn(Optional.of("8084"));
    Project projectResponse = projectDomainService.getProjectDetails(project.getFolder());

    assertThat(projectResponse.getConfig(PROJECT_NAME)).contains("Cool Project");
    assertThat(projectResponse.getConfig(PACKAGE_NAME)).contains("com.cool.project");
    assertThat(projectResponse.getConfig("serverPort")).contains(8084);

    verify(this.buildToolService, times(1)).getGroup(Mockito.any());
    verify(this.buildToolService, never()).getName(Mockito.any());
  }
}
