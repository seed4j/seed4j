package tech.jhipster.lite.generator.server.springboot.springcloud.consul.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPomXml;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class ConsulDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @Mock
  BuildToolService buildToolService;

  @Mock
  SpringCloudCommonService springCloudCommonService;

  @Mock
  DockerService dockerService;

  @InjectMocks
  ConsulDomainService consulDomainService;

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPomXml();
    when(buildToolService.getVersion(project, "spring-cloud")).thenReturn(Optional.of("0.0.0"));
    when(dockerService.getImageNameWithVersion("consul")).thenReturn(Optional.of("1.1.1"));
    when(dockerService.getImageNameWithVersion("jhipster/consul-config-loader")).thenReturn(Optional.of("2.2.2"));

    consulDomainService.init(project);

    verify(springCloudCommonService).addSpringCloudCommonDependencies(project);
    verify(buildToolService, times(2)).addDependency(any(Project.class), any(Dependency.class));

    verify(springCloudCommonService, times(3)).addOrMergeBootstrapProperties(any(Project.class), anyString(), anyString(), anyString());
    verify(projectRepository, times(2)).template(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldNotAddDependencies() {
    Project project = tmpProjectWithPomXml();

    assertThatThrownBy(() -> consulDomainService.addDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotAddDockerComposeFile() {
    Project project = tmpProjectWithPomXml();

    assertThatThrownBy(() -> consulDomainService.addDockerConsul(project))
      .isExactlyInstanceOf(GeneratorException.class)
      .hasMessageContaining("consul");
  }
}
