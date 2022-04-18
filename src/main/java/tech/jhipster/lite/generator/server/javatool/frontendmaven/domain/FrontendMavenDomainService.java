package tech.jhipster.lite.generator.server.javatool.frontendmaven.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.TECHNICAL_INFRASTRUCTURE_PRIMARY;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_PATH;

import java.util.List;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public record FrontendMavenDomainService(BuildToolService buildToolService, ProjectRepository projectRepository)
  implements FrontendMavenService {
  private static final String SOURCE = "server/springboot/mvc/web/src";
  private static final String TEST = "server/springboot/mvc/web/test";

  @Override
  public void addFrontendMavenPlugin(Project project) {
    List
      .of("node", "npm", "frontend-maven-plugin", "checksum-maven-plugin", "maven-antrun-plugin")
      .forEach(key -> addProperty(project, key));

    buildToolService.addPlugin(project, checksumMavenPlugin());
    buildToolService.addPlugin(project, mavenAntrunPlugin());
    buildToolService.addPlugin(project, frontendMavenPlugin());

    addRedirectionToWebappFiles(project);
  }

  private void addRedirectionToWebappFiles(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(BASE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    String redirectionPath = getPath(TECHNICAL_INFRASTRUCTURE_PRIMARY, "redirection");

    projectRepository.template(project, SOURCE, "RedirectionResource.java", getPath(MAIN_JAVA, packageNamePath, redirectionPath));
    projectRepository.template(project, TEST, "RedirectionResourceIT.java", getPath(TEST_JAVA, packageNamePath, redirectionPath));
  }

  private Plugin frontendMavenPlugin() {
    return Plugin
      .builder()
      .groupId("com.github.eirslett")
      .artifactId("frontend-maven-plugin")
      .version("\\${frontend-maven-plugin.version}")
      .additionalElements(
        """
          <executions>
            <execution>
              <id>install-node-and-npm</id>
              <goals>
                <goal>install-node-and-npm</goal>
              </goals>
              <configuration>
                <nodeVersion>\\${node.version}</nodeVersion>
                <npmVersion>\\${npm.version}</npmVersion>
              </configuration>
            </execution>
            <execution>
              <id>npm install</id>
              <goals>
                <goal>npm</goal>
              </goals>
            </execution>
            <execution>
              <id>build front</id>
              <goals>
                <goal>npm</goal>
              </goals>
              <phase>generate-resources</phase>
              <configuration>
                <arguments>run build</arguments>
                <environmentVariables>
                  <APP_VERSION>\\${project.version}</APP_VERSION>
                </environmentVariables>
                <npmInheritsProxyConfigFromMaven>false</npmInheritsProxyConfigFromMaven>
              </configuration>
            </execution>
            <execution>
              <id>front test</id>
              <goals>
                <goal>npm</goal>
              </goals>
              <phase>test</phase>
              <configuration>
                <arguments>run test</arguments>
                <npmInheritsProxyConfigFromMaven>false</npmInheritsProxyConfigFromMaven>
              </configuration>
            </execution>
          </executions>
          """
      )
      .build();
  }

  private Plugin mavenAntrunPlugin() {
    return Plugin
      .builder()
      .groupId("org.apache.maven.plugins")
      .artifactId("maven-antrun-plugin")
      .version("\\${maven-antrun-plugin.version}")
      .additionalElements(
        """
          <executions>
            <execution>
              <id>eval-frontend-checksum</id>
              <phase>generate-resources</phase>
              <goals>
                <goal>run</goal>
              </goals>
              <configuration>
                <target>
                  <condition property="skip.npm" value="true" else="false">
                    <and>
                      <available file="checksums.csv" filepath="\\${project.build.directory}" />
                      <available file="checksums.csv.old" filepath="\\${project.build.directory}" />
                      <filesmatch file1="\\${project.build.directory}/checksums.csv" file2="\\${project.build.directory}/checksums.csv.old" />
                    </and>
                  </condition>
                </target>
                <exportAntProperties>true</exportAntProperties>
              </configuration>
            </execution>
          </executions>
          """
      )
      .build();
  }

  private Plugin checksumMavenPlugin() {
    return Plugin
      .builder()
      .groupId("net.nicoulaj.maven.plugins")
      .artifactId("checksum-maven-plugin")
      .version("\\${checksum-maven-plugin.version}")
      .additionalElements(
        """
          <executions>
            <execution>
              <id>create-pre-compiled-webapp-checksum</id>
              <phase>generate-resources</phase>
              <goals>
                <goal>files</goal>
              </goals>
            </execution>
            <execution>
              <id>create-compiled-webapp-checksum</id>
              <goals>
                <goal>files</goal>
              </goals>
              <phase>compile</phase>
              <configuration>
                <csvSummaryFile>checksums.csv.old</csvSummaryFile>
              </configuration>
            </execution>
          </executions>
          <configuration>
            <fileSets>
              <fileSet>
                <directory>\\${project.basedir}</directory>
                <includes>
                  <include>src/main/webapp/**/*.*</include>
                  <include>target/classes/static/**/*.*</include>
                  <include>package-lock.json</include>
                  <include>package.json</include>
                  <include>tsconfig.json</include>
                </includes>
              </fileSet>
            </fileSets>
            <failOnError>false</failOnError>
            <failIfNoFiles>false</failIfNoFiles>
            <individualFiles>false</individualFiles>
            <algorithms>
              <algorithm>SHA-1</algorithm>
            </algorithms>
            <includeRelativePath>true</includeRelativePath>
            <quiet>true</quiet>
          </configuration>
          """
      )
      .build();
  }

  private void addProperty(Project project, String key) {
    buildToolService
      .getVersion(project, key)
      .ifPresentOrElse(
        version -> buildToolService.addProperty(project, key + ".version", version),
        () -> {
          throw new GeneratorException("Version not found: " + key);
        }
      );
  }
}
