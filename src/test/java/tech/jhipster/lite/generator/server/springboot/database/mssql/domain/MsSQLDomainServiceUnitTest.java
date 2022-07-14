package tech.jhipster.lite.generator.server.springboot.database.mssql.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.docker.domain.DockerImage;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonService;

@UnitTest
@ExtendWith(MockitoExtension.class)
class MsSQLDomainServiceUnitTest {

  @Mock
  BuildToolService buildToolService;

  @Mock
  SpringBootCommonService springBootCommonService;

  @Mock
  SQLCommonService sqlCommonService;

  @Mock
  ProjectRepository projectRepository;

  @Mock
  DockerImages dockerImages;

  @InjectMocks
  MsSQLDomainService msSQLDomainService;

  @Test
  void shouldNotInitWithoutProject() {
    assertThatThrownBy(() -> msSQLDomainService.init(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPomXml();

    when(dockerImages.get("mcr.microsoft.com/mssql/server")).thenReturn(new DockerImage("mssql", "0.0.0"));

    msSQLDomainService.init(project);

    verify(buildToolService).addDependency(any(Project.class), any(Dependency.class));

    verify(springBootCommonService, times(4)).addLoggerTest(any(Project.class), anyString(), any(Level.class));

    verify(sqlCommonService).addSpringDataJpa(project);
    verify(sqlCommonService).addHibernateCore(project);
    verify(sqlCommonService).addDockerComposeTemplate(project, "mssql");
    verify(sqlCommonService).addJavaFiles(project, "mssql");
    verify(projectRepository).add(any(ProjectFile.class));
    verify(springBootCommonService, times(7)).addProperties(eq(project), any(), any());
    verify(springBootCommonService, times(2)).addLogger(eq(project), any(), any());
    verify(sqlCommonService).addLoggers(project);
    verify(projectRepository).template(any(ProjectFile.class));
    verify(springBootCommonService).updateIntegrationTestAnnotation(project, "MsSQLTestContainerExtension");
    verify(projectRepository).template(any(ProjectFile.class));
    verify(projectRepository).add(any(ProjectFile.class));
  }
}
