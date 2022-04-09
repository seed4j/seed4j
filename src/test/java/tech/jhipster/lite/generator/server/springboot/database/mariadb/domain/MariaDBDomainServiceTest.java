package tech.jhipster.lite.generator.server.springboot.database.mariadb.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyMap;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPomXml;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonService;
import tech.jhipster.lite.generator.tools.domain.Project;

@UnitTest
@ExtendWith(MockitoExtension.class)
class MariaDBDomainServiceTest {

  @Mock
  BuildToolService buildToolService;

  @Mock
  SpringBootCommonService springBootCommonService;

  @Mock
  SQLCommonService sqlCommonService;

  @Mock
  DockerService dockerService;

  @InjectMocks
  MariaDBDomainService mariaDBDomainService;

  @Test
  void shouldNotInitWithoutProject() {
    assertThatThrownBy(() -> mariaDBDomainService.init(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPomXml();

    when(dockerService.getImageNameWithVersion("mariadb")).thenReturn(Optional.of("mariadb:0.0.0"));

    mariaDBDomainService.init(project);

    verify(buildToolService).addDependency(any(Project.class), any(Dependency.class));

    verify(springBootCommonService, times(2)).addLoggerTest(any(Project.class), anyString(), any(Level.class));

    verify(sqlCommonService).addTestcontainers(any(Project.class), anyString(), anyMap());
    verify(sqlCommonService).addSpringDataJpa(project);
    verify(sqlCommonService).addHikari(project);
    verify(sqlCommonService).addHibernateCore(project);
    verify(sqlCommonService).addDockerComposeTemplate(project, "mariadb");
    verify(sqlCommonService).addJavaFiles(project, "mariadb");
    verify(sqlCommonService).addProperties(eq(project), any());
    verify(sqlCommonService).addLoggers(project);
  }

  @Test
  void shouldThrowExceptionWhenImageVersionNotFound() {
    Project project = tmpProject();

    assertThatThrownBy(() -> mariaDBDomainService.init(project)).isInstanceOf(GeneratorException.class).hasMessageContaining("mariadb");
  }
}
