package tech.jhipster.lite.generator.typescript.application;

import static tech.jhipster.lite.TestUtils.tmpProjectWithPackageJsonComplete;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class TypescriptApplicationServiceIT {

  @Autowired
  TypescriptApplicationService typescriptApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPackageJsonComplete();

    typescriptApplicationService.init(project);

    TypescriptAssert.assertDevDependencies(project);
    TypescriptAssert.assertScripts(project);
    TypescriptAssert.assertConfigFiles(project);
  }
}
