package tech.jhipster.lite.generator.server.springboot.async.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_RESOURCES;

import java.util.List;
import tech.jhipster.lite.generator.tools.domain.DefaultConfig;
import tech.jhipster.lite.generator.tools.domain.Project;

public class SpringBootAsyncAssert {

  public static void assertJavaFiles(Project project) {
    String basePackage = project.getPackageName().orElse(DefaultConfig.DEFAULT_PACKAGE_NAME);
    String asyncPackage = basePackage + ".technical.infrastructure.secondary.async";

    String basePath = project.getPackageNamePath().orElse(getPath(DefaultConfig.PACKAGE_PATH));
    String asyncPath = getPath(MAIN_JAVA, basePath, "technical/infrastructure/secondary/async");

    assertFileExist(project, getPath(asyncPath, "AsyncConfiguration.java"));

    assertFileContent(project, getPath(asyncPath, "AsyncConfiguration.java"), "package " + asyncPackage);
  }

  public static void assertProperties(Project project) {
    String baseName = project.getBaseName().orElse("jhipster");

    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config/application.properties"),
      List.of(
        "spring.task.execution.pool.keep-alive=10s",
        "spring.task.execution.pool.max-size=16",
        "spring.task.execution.pool.queue-capacity=100",
        "spring.task.execution.thread-name-prefix=" + baseName + "-task-",
        "spring.task.scheduling.pool.size=2",
        "spring.task.scheduling.thread-name-prefix=" + baseName + "-scheduling-"
      )
    );
  }
}
