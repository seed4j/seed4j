package tech.jhipster.lite.generator.server.springboot.springcloud.configclient.domain;

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
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;
import tech.jhipster.lite.generator.tools.domain.Project;

@UnitTest
@ExtendWith(MockitoExtension.class)
class SpringCloudConfigClientDomainServiceTest {

  @Mock
  BuildToolService buildToolService;

  @Mock
  SpringCloudCommonService springCloudCommonService;

  @InjectMocks
  SpringCloudConfigClientDomainService springCloudConfigClientDomainService;

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPomXml();
    when(buildToolService.getVersion(project, "spring-cloud")).thenReturn(Optional.of("0.0.0"));

    springCloudConfigClientDomainService.init(project);

    verify(springCloudCommonService).addSpringCloudCommonDependencies(project);
    verify(buildToolService, times(1)).addDependency(any(Project.class), any(Dependency.class));

    verify(springCloudCommonService, times(3)).addOrMergeBootstrapProperties(any(Project.class), anyString(), anyString(), anyString());
    verify(springCloudCommonService).addJhipsterRegistryDockerCompose(any(Project.class));
  }

  @Test
  void shouldNotAddCloudConfigDependencies() {
    Project project = tmpProjectWithPomXml();

    assertThatThrownBy(() -> springCloudConfigClientDomainService.addCloudConfigDependencies(project))
      .isExactlyInstanceOf(GeneratorException.class);
  }
}
