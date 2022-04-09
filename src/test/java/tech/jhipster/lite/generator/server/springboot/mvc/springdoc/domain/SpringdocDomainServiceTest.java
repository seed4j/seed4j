package tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.API_DESCRIPTION_CONFIG_KEY;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.API_EXT_DOC_DESCRIPTION_CONFIG_KEY;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.API_EXT_DOC_URL_CONFIG_KEY;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.API_LICENSE_NAME_CONFIG_KEY;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.API_LICENSE_URL_CONFIG_KEY;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.API_TITLE_CONFIG_KEY;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_API_DESCRIPTION;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_API_TITLE;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_EXT_DOC_DESCRIPTION;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_EXT_DOC_URL;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_LICENSE_NAME;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_LICENSE_URL;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_SWAGGER_UI_SORT_VALUE;
import static tech.jhipster.lite.generator.server.springboot.mvc.springdoc.domain.SpringdocConstants.DEFAULT_TRY_OUT_ENABLED;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;

import java.util.HashMap;
import java.util.Map;
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
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class SpringdocDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @Mock
  BuildToolService buildToolService;

  @Mock
  SpringBootCommonService springBootCommonService;

  @InjectMocks
  SpringdocDomainService springdocDomainService;

  @Test
  void shouldInitWithDefaultValues() {
    // Given
    Map<String, Object> config = new HashMap<>();
    config.put(BASE_NAME, "foo");
    Project project = Project.builder().folder("/path/to/folder").config(config).build();

    // When
    when(buildToolService.getVersion(project, "springdoc-openapi")).thenReturn(Optional.of("0.0.0"));
    springdocDomainService.init(project);

    // Then
    verify(buildToolService).addProperty(project, "springdoc-openapi-ui.version", "0.0.0");
    ArgumentCaptor<Dependency> dependencyArgCaptor = ArgumentCaptor.forClass(Dependency.class);
    verify(buildToolService).addDependency(eq(project), dependencyArgCaptor.capture());
    assertThat(dependencyArgCaptor.getValue()).usingRecursiveComparison().isEqualTo(getExpectedDependency());

    verify(projectRepository)
      .template(
        project,
        "server/springboot/mvc/springdoc/src",
        "SpringdocConfiguration.java",
        "src/main/java/com/mycompany/myapp/technical/infrastructure/primary/springdoc"
      );

    verify(springBootCommonService, times(3)).addProperties(any(), anyString(), any());
    verify(springBootCommonService).addProperties(project, "springdoc.swagger-ui.operationsSorter", DEFAULT_SWAGGER_UI_SORT_VALUE);
    verify(springBootCommonService).addProperties(project, "springdoc.swagger-ui.tagsSorter", DEFAULT_SWAGGER_UI_SORT_VALUE);
    verify(springBootCommonService).addProperties(project, "springdoc.swagger-ui.tryItOutEnabled", DEFAULT_TRY_OUT_ENABLED);

    assertThat(project.getConfig())
      .containsEntry("baseNameLowercase", "foo")
      .containsEntry(API_TITLE_CONFIG_KEY, DEFAULT_API_TITLE)
      .containsEntry(API_DESCRIPTION_CONFIG_KEY, DEFAULT_API_DESCRIPTION)
      .containsEntry(API_LICENSE_NAME_CONFIG_KEY, DEFAULT_LICENSE_NAME)
      .containsEntry(API_LICENSE_URL_CONFIG_KEY, DEFAULT_LICENSE_URL)
      .containsEntry(API_EXT_DOC_DESCRIPTION_CONFIG_KEY, DEFAULT_EXT_DOC_DESCRIPTION)
      .containsEntry(API_EXT_DOC_URL_CONFIG_KEY, DEFAULT_EXT_DOC_URL);
  }

  @Test
  void shouldInitWithAdditionalConfigFromProject() {
    // Given
    Map<String, Object> projectConfig = new HashMap<>();
    projectConfig.put(BASE_NAME, "MyProject");
    projectConfig.put(API_TITLE_CONFIG_KEY, "Custom API title");
    projectConfig.put(API_DESCRIPTION_CONFIG_KEY, "Custom API description");
    projectConfig.put(API_LICENSE_NAME_CONFIG_KEY, "Custom 1.0");
    projectConfig.put(API_LICENSE_URL_CONFIG_KEY, "https://custom-license-url.com");
    projectConfig.put(API_EXT_DOC_DESCRIPTION_CONFIG_KEY, "Custom external doc description");
    projectConfig.put(API_EXT_DOC_URL_CONFIG_KEY, "https://custom-ext-doc-url.com");

    Project project = Project.builder().folder("/path/to/folder").config(projectConfig).build();

    // When
    when(buildToolService.getVersion(project, "springdoc-openapi")).thenReturn(Optional.of("0.0.0"));
    springdocDomainService.init(project);

    // Then
    verify(buildToolService).addProperty(project, "springdoc-openapi-ui.version", "0.0.0");
    ArgumentCaptor<Dependency> dependencyArgCaptor = ArgumentCaptor.forClass(Dependency.class);
    verify(buildToolService).addDependency(eq(project), dependencyArgCaptor.capture());
    assertThat(dependencyArgCaptor.getValue()).usingRecursiveComparison().isEqualTo(getExpectedDependency());

    verify(projectRepository)
      .template(
        project,
        "server/springboot/mvc/springdoc/src",
        "SpringdocConfiguration.java",
        "src/main/java/com/mycompany/myapp/technical/infrastructure/primary/springdoc"
      );

    verify(springBootCommonService, times(3)).addProperties(any(), anyString(), any());
    verify(springBootCommonService).addProperties(project, "springdoc.swagger-ui.operationsSorter", DEFAULT_SWAGGER_UI_SORT_VALUE);
    verify(springBootCommonService).addProperties(project, "springdoc.swagger-ui.tagsSorter", DEFAULT_SWAGGER_UI_SORT_VALUE);
    verify(springBootCommonService).addProperties(project, "springdoc.swagger-ui.tryItOutEnabled", DEFAULT_TRY_OUT_ENABLED);

    assertThat(project.getConfig()).containsAllEntriesOf(projectConfig);
    assertThat(project.getConfig()).containsEntry("baseNameLowercase", "myProject");
  }

  @Test
  void shouldNotAddSpringdocDependency() {
    Project project = tmpProject();

    assertThatThrownBy(() -> springdocDomainService.addSpringdocDependency(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  private static Dependency getExpectedDependency() {
    return Dependency
      .builder()
      .groupId("org.springdoc")
      .artifactId("springdoc-openapi-ui")
      .version("\\${springdoc-openapi-ui.version}")
      .build();
  }
}
