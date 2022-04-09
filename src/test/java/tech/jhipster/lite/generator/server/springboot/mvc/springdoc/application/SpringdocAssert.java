package tech.jhipster.lite.generator.server.springboot.mvc.springdoc.application;

import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.tools.domain.Constants.POM_XML;

import java.util.List;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.generator.tools.domain.DefaultConfig;
import tech.jhipster.lite.generator.tools.domain.Project;

public class SpringdocAssert {

  public static final String SPRINGDOC_CONFIGURATION_JAVA = "SpringdocConfiguration.java";

  public static void assertDependencies(Project project) {
    TestUtils.assertFileContent(project, POM_XML, List.of("<springdoc-openapi-ui.version>"));
    TestUtils.assertFileContent(
      project,
      POM_XML,
      List.of(
        "<dependency>",
        "<groupId>org.springdoc</groupId>",
        "<artifactId>springdoc-openapi-ui</artifactId>",
        "<version>${springdoc-openapi-ui.version}</version>",
        "</dependency>"
      )
    );
  }

  public static void assertJavaFiles(Project project) {
    String basePackage = project.getPackageName().orElse(DefaultConfig.DEFAULT_PACKAGE_NAME);
    String springdocPackage = basePackage + ".technical.infrastructure.primary.springdoc";

    String basePath = project.getPackageNamePath().orElse(getPath(DefaultConfig.PACKAGE_PATH));
    String springdocPath = getPath(MAIN_JAVA, basePath, "technical/infrastructure/primary/springdoc");

    String springdocConfigJavaFilePath = getPath(springdocPath, SPRINGDOC_CONFIGURATION_JAVA);
    assertFileExist(project, springdocConfigJavaFilePath);
    TestUtils.assertFileContent(project, springdocConfigJavaFilePath, "package " + springdocPackage);
  }

  public static void assertJavaFilesWithSecurityJWT(Project project) {
    String basePackage = project.getPackageName().orElse(DefaultConfig.DEFAULT_PACKAGE_NAME);
    String springdocPackage = basePackage + ".technical.infrastructure.primary.springdoc";

    String basePath = project.getPackageNamePath().orElse(getPath(DefaultConfig.PACKAGE_PATH));
    String springdocPath = getPath(MAIN_JAVA, basePath, "technical/infrastructure/primary/springdoc");

    String springdocConfigJavaFilePath = getPath(springdocPath, SPRINGDOC_CONFIGURATION_JAVA);
    assertFileExist(project, springdocConfigJavaFilePath);
    TestUtils.assertFileContent(project, springdocConfigJavaFilePath, "package " + springdocPackage);
    TestUtils.assertFileContent(project, springdocConfigJavaFilePath, "bearer-jwt");
  }

  public static void assertProperties(Project project) {
    TestUtils.assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config/application.properties"),
      List.of(
        "springdoc.swagger-ui.operationsSorter=alpha",
        "springdoc.swagger-ui.tagsSorter=alpha",
        "springdoc.swagger-ui.tryItOutEnabled=true"
      )
    );
  }

  public static void assertFileContent(Project project, String filename, String expectedValue) {
    String basePath = project.getPackageNamePath().orElse(getPath(DefaultConfig.PACKAGE_PATH));
    String springdocPath = getPath(MAIN_JAVA, basePath, "technical/infrastructure/primary/springdoc");

    TestUtils.assertFileContent(project, getPath(springdocPath, filename), expectedValue);
  }
}
