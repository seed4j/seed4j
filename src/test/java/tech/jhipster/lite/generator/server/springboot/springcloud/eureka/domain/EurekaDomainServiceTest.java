package tech.jhipster.lite.generator.server.springboot.springcloud.eureka.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.tmpProject;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
class EurekaDomainServiceTest {

  @Mock
  BuildToolService buildToolService;

  @Mock
  SpringCloudCommonService springCloudCommonService;

  @InjectMocks
  EurekaDomainService eurekaDomainService;

  @Test
  void shouldInit() {
    // Given
    Project project = tmpProject();

    when(buildToolService.getVersion(project, "spring-cloud-netflix-eureka-client")).thenReturn(Optional.of("1.1.1"));

    // When
    eurekaDomainService.init(project);

    // Then
    verify(springCloudCommonService).addSpringCloudCommonDependencies(project);

    verify(buildToolService).addProperty(project, "spring-cloud-netflix-eureka-client.version", "1.1.1");

    ArgumentCaptor<Dependency> dependencyArgCaptor = ArgumentCaptor.forClass(Dependency.class);
    verify(buildToolService).addDependency(eq(project), dependencyArgCaptor.capture());
    assertThat(dependencyArgCaptor.getValue()).usingRecursiveComparison().isEqualTo(getExpectedNetflixEurekaDependency());

    verify(springCloudCommonService, times(2)).addOrMergeBootstrapProperties(any(Project.class), anyString(), anyString(), anyString());
    verify(springCloudCommonService)
      .addOrMergeBootstrapProperties(
        project,
        "server/springboot/springcloud/eureka/src",
        "bootstrap.properties",
        "src/main/resources/config"
      );
    verify(springCloudCommonService)
      .addOrMergeBootstrapProperties(
        project,
        "server/springboot/springcloud/eureka/src/test",
        "bootstrap.properties",
        "src/test/resources/config"
      );

    verify(springCloudCommonService).addJhipsterRegistryDockerCompose(project);
  }

  @Test
  void shouldThrowExceptionOnInitWhenEurekaClientDependencyVersionNotFound() {
    // Given
    Project project = tmpProject();

    when(buildToolService.getVersion(project, "spring-cloud-netflix-eureka-client")).thenReturn(Optional.empty());

    // When + Then
    assertThatThrownBy(() -> eurekaDomainService.init(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  private Dependency getExpectedNetflixEurekaDependency() {
    return Dependency
      .builder()
      .groupId("org.springframework.cloud")
      .artifactId("spring-cloud-starter-netflix-eureka-client")
      .version("\\${spring-cloud-netflix-eureka-client.version}")
      .build();
  }
}
