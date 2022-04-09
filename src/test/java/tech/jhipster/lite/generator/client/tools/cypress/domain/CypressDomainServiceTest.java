package tech.jhipster.lite.generator.client.tools.cypress.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.generator.client.tools.cypress.domain.Cypress.JAVASCRIPT_INTEGRATION;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.client.common.domain.ClientCommonService;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class CypressDomainServiceTest {

  @InjectMocks
  CypressDomainService cypressDomainService;

  @Mock
  NpmService npmService;

  @Mock
  ProjectRepository projectRepository;

  @Mock
  ClientCommonService clientCommonService;

  @Test
  void shouldAddCypress() {
    Project project = tmpProject();
    when(npmService.getVersionInCommon("cypress")).thenReturn(Optional.of("0.0.0"));
    when(npmService.getVersionInCommon("eslint-plugin-cypress")).thenReturn(Optional.of("1.1.1"));

    cypressDomainService.init(project);

    verify(npmService, times(2)).addDevDependency(any(Project.class), anyString(), anyString());
    verify(npmService).addDevDependency(project, "cypress", "0.0.0");
    verify(npmService).addDevDependency(project, "eslint-plugin-cypress", "1.1.1");

    verify(npmService, times(4)).addScript(any(Project.class), anyString(), anyString());
    verify(npmService).addScript(project, "e2e", "npm run test:component");
    verify(npmService).addScript(project, "e2e:headless", "npm run test:component:headless");
    verify(npmService)
      .addScript(project, "test:component", "cypress open --config-file src/test/javascript/integration/cypress-config.json");
    verify(npmService)
      .addScript(
        project,
        "test:component:headless",
        "cypress run --headless --config-file src/test/javascript/integration/cypress-config.json"
      );

    verify(projectRepository, times(2)).add(any(Project.class), anyString(), anyString(), anyString());
    verify(projectRepository).add(project, "client/common/cypress/src/test/javascript/integration", ".eslintrc.js", JAVASCRIPT_INTEGRATION);
    verify(projectRepository)
      .add(project, "client/common/cypress/src/test/javascript/integration", "tsconfig.json", JAVASCRIPT_INTEGRATION);

    verify(projectRepository)
      .template(project, "client/common/cypress/src/test/javascript/integration", "cypress-config.json", JAVASCRIPT_INTEGRATION);
    verify(projectRepository)
      .template(
        project,
        "client/common/cypress/src/test/javascript/integration/common/primary/app",
        "Home.spec.ts",
        "src/test/javascript/integration/common/primary/app"
      );

    verify(clientCommonService).excludeInTsconfigJson(project, "src/test/javascript/integration/**/*spec.ts");
  }

  @Test
  void shouldNotInit() {
    Project project = tmpProject();

    assertThatThrownBy(() -> cypressDomainService.init(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotAddDevDependencies() {
    Project project = tmpProject();

    assertThatThrownBy(() -> cypressDomainService.addDevDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }
}
