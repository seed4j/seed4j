package tech.jhipster.lite.generator.server.springboot.docker.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPomXml;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class SpringBootDockerDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @Mock
  BuildToolService buildToolService;

  @InjectMocks
  SpringBootDockerDomainService springBootDockerDomainService;

  @Test
  void shouldAddJib() {
    Project project = tmpProjectWithPomXml();
    when(buildToolService.getVersion(any(Project.class), anyString())).thenReturn(Optional.of("0.0.0"));

    springBootDockerDomainService.addJib(project);

    verify(buildToolService, times(3)).addProperty(any(Project.class), anyString(), anyString());
    verify(buildToolService).addPlugin(any(Project.class), any(Plugin.class));

    verify(projectRepository).template(any(Project.class), anyString(), anyString(), anyString());
  }

  @Test
  void shouldNotAddJibPlugin() {
    Project project = tmpProjectWithPomXml();

    Assertions.assertThatThrownBy(() -> springBootDockerDomainService.addJibPlugin(project)).isExactlyInstanceOf(GeneratorException.class);
  }
}
