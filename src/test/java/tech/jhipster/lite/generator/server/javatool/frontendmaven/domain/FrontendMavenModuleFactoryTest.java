package tech.jhipster.lite.generator.server.javatool.frontendmaven.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class FrontendMavenModuleFactoryTest {

  private static final FrontendMavenModuleFactory factory = new FrontendMavenModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing("    <node.version>")
      .containing("    <npm.version>")
      .containing(
        """
              <plugin>
                <groupId>net.nicoulaj.maven.plugins</groupId>
                <artifactId>checksum-maven-plugin</artifactId>
                <version>${checksum-maven-plugin.version}</version>
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
              </plugin>
        """
      )
      .containing(
        """
              <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-antrun-plugin</artifactId>
                <version>${maven-antrun-plugin.version}</version>
                <executions>
                  <execution>
                    <id>eval-frontend-checksum</id>
                    <phase>generate-resources</phase>
                    <goals>
                      <goal>run</goal>
                    </goals>
                    <configuration>
                      <target>
                        <condition else="false" property="skip.npm" value="true">
                          <and>
                            <available file="checksums.csv" filepath="${project.build.directory}"/>
                            <available file="checksums.csv.old" filepath="${project.build.directory}"/>
                            <filesmatch file1="${project.build.directory}/checksums.csv" file2="${project.build.directory}/checksums.csv.old"/>
                          </and>
                        </condition>
                      </target>
                      <exportAntProperties>true</exportAntProperties>
                    </configuration>
                  </execution>
                </executions>
              </plugin>
        """
      )
      .containing(
        """
              <plugin>
                <groupId>com.github.eirslett</groupId>
                <artifactId>frontend-maven-plugin</artifactId>
                <version>${frontend-maven-plugin.version}</version>
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
              </plugin>
        """
      )
      .and()
      .hasJavaSources("com/jhipster/test/technical/infrastructure/primary/redirection/RedirectionResource.java")
      .hasJavaTests("com/jhipster/test/technical/infrastructure/primary/redirection/RedirectionResourceIT.java");
  }
}
