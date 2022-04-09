package tech.jhipster.lite.generator.server.springboot.mvc.dummy.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static tech.jhipster.lite.TestUtils.tmpProject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class DummyDomainServiceTest {

  @Mock
  private ProjectRepository projectRepository;

  @InjectMocks
  DummyDomainService dummyDomainService;

  @Test
  void shouldApplyDummyGitPatch() {
    Project project = tmpProject();

    dummyDomainService.applyDummyGitPatch(project);

    verify(projectRepository).gitInit(any(Project.class));
    verify(projectRepository).template(any(Project.class), anyString(), anyString(), anyString());
    verify(projectRepository).gitApplyPatch(any(Project.class), anyString());
  }
}
