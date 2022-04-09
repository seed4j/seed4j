package tech.jhipster.lite.generator.server.javatool.jacoco.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class JacocoDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @InjectMocks
  JacocoDomainService jacocoDomainService;

  @Test
  void shouldAddCheckMinimumCoverage() {
    Project project = TestUtils.tmpProjectWithPomXml();

    jacocoDomainService.addCheckMinimumCoverage(project);

    verify(projectRepository).gitInit(any(Project.class));
    verify(projectRepository).add(any(Project.class), anyString(), anyString(), anyString());
    verify(projectRepository).gitApplyPatch(any(Project.class), anyString());
  }
}
