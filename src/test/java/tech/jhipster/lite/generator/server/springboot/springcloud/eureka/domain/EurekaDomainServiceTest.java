package tech.jhipster.lite.generator.server.springboot.springcloud.eureka.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.tmpProject;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.Base64Utils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;

@UnitTest
@ExtendWith(MockitoExtension.class)
class EurekaDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

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

    String expectedBase64Secret = "encodedSecret";

    when(buildToolService.getVersion(project, "spring-cloud")).thenReturn(Optional.of("0.0.0"));
    when(buildToolService.getVersion(project, "spring-cloud-netflix-eureka-client")).thenReturn(Optional.of("1.1.1"));

    try (MockedStatic<Base64Utils> base64Utils = mockStatic(Base64Utils.class)) {
      base64Utils.when(Base64Utils::getBase64Secret).thenReturn(expectedBase64Secret);

      // When
      eurekaDomainService.init(project);

      // Then
      verify(buildToolService).addProperty(project, "spring-cloud.version", "0.0.0");
      verify(buildToolService).addProperty(project, "spring-cloud-netflix-eureka-client.version", "1.1.1");

      ArgumentCaptor<Dependency> dependencyArgCaptor = ArgumentCaptor.forClass(Dependency.class);
      verify(buildToolService).addDependencyManagement(eq(project), dependencyArgCaptor.capture());
      assertThat(dependencyArgCaptor.getValue()).usingRecursiveComparison().isEqualTo(getExpectedManagementDependency());

      dependencyArgCaptor = ArgumentCaptor.forClass(Dependency.class);
      verify(buildToolService, times(2)).addDependency(eq(project), dependencyArgCaptor.capture());
      List<Dependency> addedDependencies = dependencyArgCaptor.getAllValues();
      assertThat(addedDependencies.get(0)).usingRecursiveComparison().isEqualTo(getExpectedStarterBootstrapDependency());
      assertThat(addedDependencies.get(1)).usingRecursiveComparison().isEqualTo(getExpectedNetflixEurekaDependency());

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

      assertThat(project.getConfig("jhipsterRegistryDockerImage")).contains("jhipster/jhipster-registry:v7.1.0");
      assertThat(project.getConfig("base64JwtSecret")).contains(expectedBase64Secret);

      verify(projectRepository, times(2)).template(any(Project.class), anyString(), anyString(), anyString(), anyString());
      verify(projectRepository)
        .template(
          project,
          "server/springboot/springcloud/configclient",
          "jhipster-registry.yml",
          "src/main/docker",
          "jhipster-registry.yml"
        );

      verify(projectRepository)
        .template(
          project,
          "server/springboot/springcloud/configclient",
          "application.config.properties",
          "src/main/docker/central-server-config/localhost-config",
          "application.properties"
        );
    }
  }

  @Test
  void shouldThrowExceptionOnInitWhenSpringCloudDependencyVersionNotFound() {
    // Given
    Project project = tmpProject();

    when(buildToolService.getVersion(project, "spring-cloud")).thenReturn(Optional.empty());

    // When + Then
    assertThatThrownBy(() -> eurekaDomainService.init(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldThrowExceptionOnInitWhenEurekaClientDependencyVersionNotFound() {
    // Given
    Project project = tmpProject();

    when(buildToolService.getVersion(project, "spring-cloud")).thenReturn(Optional.of("0.0.0"));
    when(buildToolService.getVersion(project, "spring-cloud-netflix-eureka-client")).thenReturn(Optional.empty());

    // When + Then
    assertThatThrownBy(() -> eurekaDomainService.init(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  private Dependency getExpectedManagementDependency() {
    return Dependency
      .builder()
      .groupId("org.springframework.cloud")
      .artifactId("spring-cloud-dependencies")
      .version("\\${spring-cloud.version}")
      .type("pom")
      .scope("import")
      .build();
  }

  private Dependency getExpectedStarterBootstrapDependency() {
    return Dependency.builder().groupId("org.springframework.cloud").artifactId("spring-cloud-starter-bootstrap").build();
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
