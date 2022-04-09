package tech.jhipster.lite.generator.server.springboot.database.mongodb.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPomXml;

import java.util.Map;
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
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class MongodbDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @Mock
  BuildToolService buildToolService;

  @Mock
  SpringBootCommonService springBootCommonService;

  @Mock
  SQLCommonService sqlCommonService;

  @Mock
  DockerService dockerService;

  @InjectMocks
  MongodbDomainService mongodbDomainService;

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPomXml();

    when(dockerService.getImageNameWithVersion("mongo")).thenReturn(Optional.of("mongo:0.0.0"));

    mongodbDomainService.init(project);

    verify(buildToolService, times(2)).addDependency(any(Project.class), any(Dependency.class));

    verify(projectRepository, times(6)).template(any(Project.class), anyString(), anyString(), anyString());

    verify(springBootCommonService).addPropertiesComment(any(Project.class), anyString());
    verify(springBootCommonService, times(2)).addProperties(any(Project.class), anyString(), any());
    verify(springBootCommonService).addPropertiesNewLine(any(Project.class));
    verify(springBootCommonService, times(2)).addLogger(any(Project.class), anyString(), any(Level.class));
    verify(springBootCommonService, times(4)).addLoggerTest(any(Project.class), anyString(), any(Level.class));
    verify(springBootCommonService).updateIntegrationTestAnnotation(any(Project.class), anyString());

    verify(sqlCommonService).addDockerComposeTemplate(project, "mongodb");
    verify(sqlCommonService).addTestcontainers(project, "mongodb", Map.of());
  }

  @Test
  void shouldThrowExceptionWhenImageVersionNotFound() {
    Project project = tmpProject();

    Assertions
      .assertThatThrownBy(() -> mongodbDomainService.init(project))
      .isInstanceOf(GeneratorException.class)
      .hasMessageContaining("mongo");
  }
}
