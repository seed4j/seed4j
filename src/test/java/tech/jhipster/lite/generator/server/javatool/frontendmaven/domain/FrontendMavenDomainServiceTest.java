package tech.jhipster.lite.generator.server.javatool.frontendmaven.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPomXml;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class FrontendMavenDomainServiceTest {

  @Mock
  BuildToolService buildToolService;

  @Mock
  ProjectRepository projectRepository;

  @InjectMocks
  FrontendMavenDomainService frontendMavenDomainService;

  @Test
  void shouldAddFrontendMavenPlugin() {
    Project project = tmpProjectWithPomXml();
    when(buildToolService.getVersion(any(Project.class), anyString())).thenReturn(Optional.of("0.0.0"));

    frontendMavenDomainService.addFrontendMavenPlugin(project);

    ArgumentCaptor<String> redirectionFilesCaptor = ArgumentCaptor.forClass(String.class);
    verify(buildToolService, times(5)).addProperty(any(Project.class), anyString(), anyString());
    verify(buildToolService, times(3)).addPlugin(any(Project.class), any(Plugin.class));
    verify(projectRepository, times(2)).template(eq(project), anyString(), redirectionFilesCaptor.capture(), anyString());

    assertThat(redirectionFilesCaptor.getAllValues()).containsExactly("RedirectionResource.java", "RedirectionResourceIT.java");
  }

  @Test
  void shouldNotAddFrontendMavenPlugin() {
    Project project = tmpProjectWithPomXml();

    Assertions
      .assertThatThrownBy(() -> frontendMavenDomainService.addFrontendMavenPlugin(project))
      .isExactlyInstanceOf(GeneratorException.class);
  }
}
