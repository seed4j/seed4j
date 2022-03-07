package tech.jhipster.lite.generator.server.springboot.database.cassandra.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
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
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.database.mongodb.domain.MongodbDomainService;
import tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonService;

@UnitTest
@ExtendWith(MockitoExtension.class)
class CassandraDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @Mock
  BuildToolService buildToolService;

  @Mock
  SQLCommonService sqlCommonService;

  @InjectMocks
  CassandraDomainService cassandraDomainService;

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPomXml();

    cassandraDomainService.init(project);

    verify(buildToolService).addDependency(any(Project.class), any(Dependency.class));
    verify(sqlCommonService).addDockerComposeTemplate(project, "cassandra");
    verify(projectRepository, times(2)).add(any(Project.class), anyString(), anyString(), anyString(), anyString());
    verify(projectRepository, times(2)).template(any(Project.class), anyString(), anyString(), anyString(), anyString());
    verify(projectRepository).add(any(Project.class), anyString(), anyString(), anyString());
    verify(projectRepository).template(any(Project.class), anyString(), anyString(), anyString());
  }
}
