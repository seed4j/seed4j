package tech.jhipster.lite.generator.server.springboot.springcloud.consul.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
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
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;

@UnitTest
@ExtendWith(MockitoExtension.class)
class ConsulDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @Mock
  BuildToolService buildToolService;

  @Mock
  SpringCloudCommonService springCloudCommonService;

  @InjectMocks
  ConsulDomainService consulDomainService;

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPomXml();
    when(buildToolService.getVersion(project, "spring-cloud")).thenReturn(Optional.of("0.0.0"));

    consulDomainService.init(project);

    verify(buildToolService).addProperty(any(Project.class), anyString(), anyString());
    verify(buildToolService).addDependencyManagement(any(Project.class), any(Dependency.class));
    verify(buildToolService, times(3)).addDependency(any(Project.class), any(Dependency.class));

    verify(springCloudCommonService, times(3)).addOrMergeBootstrapProperties(any(Project.class), anyString(), anyString(), anyString());
    verify(projectRepository, times(2)).template(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldNotAddDependencies() {
    Project project = tmpProjectWithPomXml();

    assertThatThrownBy(() -> consulDomainService.addDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }
}
