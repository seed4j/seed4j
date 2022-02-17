package tech.jhipster.lite.generator.server.sonar.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.generator.project.domain.Constants.POM_XML;

import java.util.List;
import tech.jhipster.lite.generator.project.domain.Project;

public class SonarAssert {

  private SonarAssert() {}

  public static void assertFiles(Project project) {
    assertFileExist(project, "src/main/docker/sonar.yml");
    assertFileExist(project, "sonar-project.properties");
  }

  public static void assertFrontProperties(Project project) {
    assertFileContent(project, "sonar-project.properties", "sonar.testExecutionReportPaths");
    assertFileContent(project, "sonar-project.properties", "sonar.javascript.lcov.reportPaths");
  }

  public static void assertPomXml(Project project) {
    assertFileContent(project, POM_XML, "<properties-maven-plugin.version>");
    assertFileContent(project, POM_XML, "</properties-maven-plugin.version>");
    assertFileContent(project, POM_XML, "<sonar-maven-plugin.version>");
    assertFileContent(project, POM_XML, "</sonar-maven-plugin.version>");

    assertFileContent(project, POM_XML, sonarSourcePlugin());
    assertFileContent(project, POM_XML, propertiesPlugin());
  }

  private static List<String> sonarSourcePlugin() {
    return List.of(
      "<plugin>",
      "<groupId>org.sonarsource.scanner.maven</groupId>",
      "<artifactId>sonar-maven-plugin</artifactId>",
      "<version>${sonar-maven-plugin.version}</version>",
      "</plugin>"
    );
  }

  private static List<String> propertiesPlugin() {
    return List.of(
      "<plugin>",
      "<groupId>org.codehaus.mojo</groupId>",
      "<artifactId>properties-maven-plugin</artifactId>",
      "<version>${properties-maven-plugin.version}</version>",
      "<executions>",
      "<execution>",
      "<phase>initialize</phase>",
      "<goals>",
      "<goal>read-project-properties</goal>",
      "</goals>",
      "<configuration>",
      "<files>",
      "<file>sonar-project.properties</file>",
      "</files>",
      "</configuration>",
      "</execution>",
      "</executions>",
      "</plugin>"
    );
  }
}
