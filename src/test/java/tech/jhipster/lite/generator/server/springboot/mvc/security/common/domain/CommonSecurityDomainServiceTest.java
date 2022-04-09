package tech.jhipster.lite.generator.server.springboot.mvc.security.common.domain;

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
class CommonSecurityDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @InjectMocks
  CommonSecurityDomainService commonSecurityDomainService;

  @Test
  void shouldUpdateExceptionTranslator() {
    Project project = tmpProject();

    commonSecurityDomainService.updateExceptionTranslator(project);

    verify(projectRepository, times(2)).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldUpdateIntegrationTestWithMockUser() {
    Project project = tmpProject();

    commonSecurityDomainService.updateIntegrationTestWithMockUser(project);

    verify(projectRepository, times(2)).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }
}
