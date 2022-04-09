package tech.jhipster.lite.generator.server.springboot.mvc.dummy.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_JAVA;

import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.tools.domain.Project;

public class DummyAssert {

  private DummyAssert() {}

  public static void assertJavaFiles(Project project, String packagePath, String packageName) {
    assertFileContent(
      project,
      FileUtils.getPath(MAIN_JAVA, packagePath, "dummy/application/DummyApplicationService.java"),
      "package " + packageName + ".dummy.application;"
    );
    assertFileContent(
      project,
      FileUtils.getPath(MAIN_JAVA, packagePath, "dummy/domain/Dummy.java"),
      "package " + packageName + ".dummy.domain;"
    );
    assertFileContent(
      project,
      FileUtils.getPath(MAIN_JAVA, packagePath, "dummy/domain/DummyRepository.java"),
      "package " + packageName + ".dummy.domain;"
    );
    assertFileContent(
      project,
      FileUtils.getPath(MAIN_JAVA, packagePath, "dummy/infrastructure/primary/rest/DummyResource.java"),
      "package " + packageName + ".dummy.infrastructure.primary.rest;"
    );
    assertFileContent(
      project,
      FileUtils.getPath(MAIN_JAVA, packagePath, "dummy/infrastructure/secondary/DummyInMemoryRepository.java"),
      "package " + packageName + ".dummy.infrastructure.secondary;"
    );
  }

  public static void assertTestFiles(Project project, String packagePath, String packageName) {
    assertFileContent(
      project,
      FileUtils.getPath(TEST_JAVA, packagePath, "dummy/application/DummyApplicationServiceIT.java"),
      "package " + packageName + ".dummy.application;"
    );
    assertFileContent(
      project,
      FileUtils.getPath(TEST_JAVA, packagePath, "dummy/domain/DummyTest.java"),
      "package " + packageName + ".dummy.domain;"
    );
    assertFileContent(
      project,
      FileUtils.getPath(TEST_JAVA, packagePath, "dummy/infrastructure/primary/rest/DummyResourceIT.java"),
      "package " + packageName + ".dummy.infrastructure.primary.rest;"
    );
    assertFileContent(
      project,
      FileUtils.getPath(TEST_JAVA, packagePath, "dummy/infrastructure/secondary/DummyInMemoryRepositoryTest.java"),
      "package " + packageName + ".dummy.infrastructure.secondary;"
    );
  }
}
