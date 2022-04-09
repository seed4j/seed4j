package tech.jhipster.lite.generator.server.javatool.base.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
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
class JavaBaseDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @InjectMocks
  JavaBaseDomainService javaBaseDomainService;

  @Test
  void shouldAddJavaBase() {
    Project project = tmpProject();

    javaBaseDomainService.addJavaBase(project);

    verify(projectRepository, times(8)).template(any(Project.class), anyString(), anyString(), anyString());
  }
}
