package tech.jhipster.lite.generator.server.springboot.logging.logstash.domain;

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
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

@UnitTest
@ExtendWith(MockitoExtension.class)
class LogstashDomainServiceTest {

  @Mock
  SpringBootCommonService springBootCommonService;

  @Mock
  ProjectRepository projectRepository;

  @Mock
  BuildToolService buildToolService;

  @InjectMocks
  LogstashDomainService logstashDomainService;

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPomXml();
    when(buildToolService.getVersion(project, "logstash-logback-encoder")).thenReturn(Optional.of("0.0.0"));

    logstashDomainService.init(project);

    verify(buildToolService).addProperty(any(Project.class), anyString(), anyString());
    verify(buildToolService).addDependency(any(Project.class), any(Dependency.class));

    verify(projectRepository, times(7)).template(any(Project.class), anyString(), anyString(), anyString());

    verify(springBootCommonService, times(5)).addProperties(any(Project.class), anyString(), any());

    verify(springBootCommonService).addLoggerTest(any(Project.class), anyString(), any(Level.class));
  }

  @Test
  void shouldAddDependencies() {
    Project project = tmpProjectWithPomXml();
    when(buildToolService.getVersion(project, "logstash-logback-encoder")).thenReturn(Optional.of("0.0.0"));

    logstashDomainService.addDependencies(project);

    verify(buildToolService).addProperty(any(Project.class), anyString(), anyString());
    verify(buildToolService).addDependency(any(Project.class), any(Dependency.class));
  }

  @Test
  void shouldNotAddDependencies() {
    Project project = tmpProjectWithPomXml();

    assertThatThrownBy(() -> logstashDomainService.addDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddJavaFiles() {
    Project project = tmpProjectWithPomXml();

    logstashDomainService.addJavaFiles(project);

    verify(projectRepository, times(7)).template(any(Project.class), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddProperties() {
    Project project = tmpProjectWithPomXml();

    logstashDomainService.addProperties(project);

    verify(springBootCommonService, times(5)).addProperties(any(Project.class), anyString(), any());
  }

  @Test
  void shouldAddLoggerInConfiguration() {
    Project project = tmpProjectWithPomXml();

    logstashDomainService.addLoggerInConfiguration(project);

    verify(springBootCommonService).addLoggerTest(any(Project.class), anyString(), any(Level.class));
  }
}
