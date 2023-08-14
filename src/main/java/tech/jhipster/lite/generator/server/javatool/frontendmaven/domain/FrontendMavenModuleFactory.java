package tech.jhipster.lite.generator.server.javatool.frontendmaven.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuildplugin.JavaBuildPlugin;
import tech.jhipster.lite.module.domain.npm.NpmVersionSource;
import tech.jhipster.lite.module.domain.npm.NpmVersions;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class FrontendMavenModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/mvc/frontend");

  private static final String REDIRECTION_PRIMARY = "technical/infrastructure/primary/redirection";

  private final NpmVersions npmVersions;

  public FrontendMavenModuleFactory(NpmVersions npmVersions) {
    this.npmVersions = npmVersions;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    //@formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .setVersion(javaDependencyVersion("node", "v" + npmVersions.get("node", NpmVersionSource.COMMON).get()))
        .setVersion(javaDependencyVersion("npm", npmVersions.get("npm", NpmVersionSource.COMMON).get()))
        .and()
      .javaBuildPlugins()
        .plugin(checksumPlugin())
        .plugin(antrunPlugin())
        .plugin(frontendMavenPlugin())
        .and()
      .files()
        .add(
          SOURCE.template("RedirectionResource.java"),
          toSrcMainJava().append(packagePath).append(REDIRECTION_PRIMARY).append("RedirectionResource.java")
        )
        .and()
      .build();
    //@formatter:on
  }

  private JavaBuildPlugin checksumPlugin() {
    return javaBuildPlugin()
      .groupId("net.nicoulaj.maven.plugins")
      .artifactId("checksum-maven-plugin")
      .versionSlug("checksum-maven-plugin")
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
              <directory>${project.basedir}</directory>
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

  private JavaBuildPlugin antrunPlugin() {
    return javaBuildPlugin()
      .groupId("org.apache.maven.plugins")
      .artifactId("maven-antrun-plugin")
      .versionSlug("maven-antrun-plugin")
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
                    <available file="checksums.csv" filepath="${project.build.directory}" />
                    <available file="checksums.csv.old" filepath="${project.build.directory}" />
                    <filesmatch file1="${project.build.directory}/checksums.csv" file2="${project.build.directory}/checksums.csv.old" />
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

  private JavaBuildPlugin frontendMavenPlugin() {
    return javaBuildPlugin()
      .groupId("com.github.eirslett")
      .artifactId("frontend-maven-plugin")
      .versionSlug("frontend-maven-plugin")
      .additionalElements(
        """
        <executions>
          <execution>
            <id>install-node-and-npm</id>
            <goals>
              <goal>install-node-and-npm</goal>
            </goals>
            <configuration>
              <nodeVersion>${node.version}</nodeVersion>
              <npmVersion>${npm.version}</npmVersion>
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
                <APP_VERSION>${project.version}</APP_VERSION>
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
}
