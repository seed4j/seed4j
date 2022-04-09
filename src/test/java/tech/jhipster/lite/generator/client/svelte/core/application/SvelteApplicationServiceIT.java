package tech.jhipster.lite.generator.client.svelte.core.application;

import static tech.jhipster.lite.TestUtils.tmpProjectWithPackageJsonAndLintStage;
import static tech.jhipster.lite.generator.client.svelte.core.application.SvelteAssert.assertAppFiles;
import static tech.jhipster.lite.generator.client.svelte.core.application.SvelteAssert.assertAssets;
import static tech.jhipster.lite.generator.client.svelte.core.application.SvelteAssert.assertDependency;
import static tech.jhipster.lite.generator.client.svelte.core.application.SvelteAssert.assertJestSonar;
import static tech.jhipster.lite.generator.client.svelte.core.application.SvelteAssert.assertRootFiles;
import static tech.jhipster.lite.generator.client.svelte.core.application.SvelteAssert.assertScripts;
import static tech.jhipster.lite.generator.client.svelte.core.application.SvelteAssert.assertSvelteConfigFiles;
import static tech.jhipster.lite.generator.client.svelte.core.application.SvelteAssert.assertType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class SvelteApplicationServiceIT {

  @Autowired
  SvelteApplicationService svelteApplicationService;

  @Test
  void shouldAddSvelte() {
    Project project = tmpProjectWithPackageJsonAndLintStage();

    svelteApplicationService.addSvelte(project);

    assertDependency(project);
    assertScripts(project);

    assertSvelteConfigFiles(project);
    assertRootFiles(project);
    assertAppFiles(project);

    assertJestSonar(project);
    assertType(project);
  }

  @Test
  void shouldAddStyledSvelte() {
    Project project = tmpProjectWithPackageJsonAndLintStage();

    svelteApplicationService.addStyledSvelteKit(project);

    assertDependency(project);
    assertScripts(project);

    assertSvelteConfigFiles(project);
    assertRootFiles(project);
    assertAppFiles(project);
    assertAssets(project);

    assertJestSonar(project);
    assertType(project);
  }
}
