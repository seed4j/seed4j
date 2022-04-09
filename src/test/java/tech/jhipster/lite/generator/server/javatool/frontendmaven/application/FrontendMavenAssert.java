package tech.jhipster.lite.generator.server.javatool.frontendmaven.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.POM_XML;
import static tech.jhipster.lite.generator.tools.domain.Constants.TECHNICAL_INFRASTRUCTURE_PRIMARY;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_JAVA;

import java.util.List;
import tech.jhipster.lite.generator.tools.domain.DefaultConfig;
import tech.jhipster.lite.generator.tools.domain.Project;

public class FrontendMavenAssert {

  private FrontendMavenAssert() {}

  public static void assertChecksumMavenPlugin(Project project) {
    assertFileContent(project, POM_XML, "<checksum-maven-plugin.version>");
    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<plugin>",
        "<groupId>net.nicoulaj.maven.plugins</groupId>",
        "<artifactId>checksum-maven-plugin</artifactId>",
        "<version>${checksum-maven-plugin.version}</version>",
        "<executions>"
      )
    );
  }

  public static void assertMavenAntrunPlugin(Project project) {
    assertFileContent(project, POM_XML, "<maven-antrun-plugin.version>");
    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<plugin>",
        "<groupId>org.apache.maven.plugins</groupId>",
        "<artifactId>maven-antrun-plugin</artifactId>",
        "<version>${maven-antrun-plugin.version}</version>",
        "<executions>"
      )
    );
  }

  public static void assertFrontendMavenPlugin(Project project) {
    assertFileContent(project, POM_XML, "<frontend-maven-plugin.version>");
    assertFileContent(project, POM_XML, "<node.version>");
    assertFileContent(project, POM_XML, "<npm.version>");
    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<plugin>",
        "<groupId>com.github.eirslett</groupId>",
        "<artifactId>frontend-maven-plugin</artifactId>",
        "<version>${frontend-maven-plugin.version}</version>",
        "<executions>"
      )
    );
  }

  public static void assertRedirectionFiles(Project project) {
    String path = getPath(
      MAIN_JAVA,
      project.getPackageNamePath().orElse(DefaultConfig.PACKAGE_PATH),
      TECHNICAL_INFRASTRUCTURE_PRIMARY,
      "redirection"
    );
    String testPath = getPath(
      TEST_JAVA,
      project.getPackageNamePath().orElse(DefaultConfig.PACKAGE_PATH),
      TECHNICAL_INFRASTRUCTURE_PRIMARY,
      "redirection"
    );

    assertFileExist(project, path, "RedirectionResource.java");
    assertFileExist(project, testPath, "RedirectionResourceIT.java");
  }
}
