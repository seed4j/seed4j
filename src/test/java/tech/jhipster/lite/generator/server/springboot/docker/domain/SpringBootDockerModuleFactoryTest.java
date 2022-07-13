package tech.jhipster.lite.generator.server.springboot.docker.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class SpringBootDockerModuleFactoryTest {

  private static final SpringBootDockerModuleFactory factory = new SpringBootDockerModuleFactory();

  @Test
  void shouldBuildJibModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .put("serverPort", 9000)
      .build();

    JHipsterModule module = factory.buildJibModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
              <plugin>
                <groupId>com.google.cloud.tools</groupId>
                <artifactId>jib-maven-plugin</artifactId>
                <version>${jib-maven-plugin.version}</version>
                <configuration>
                  <from>
                    <image>eclipse-temurin:17-jre-focal</image>
                    <platforms>
                      <platform>
                        <architecture>amd64</architecture>
                        <os>linux</os>
                      </platform>
                    </platforms>
                  </from>
                  <to>
                    <image>myapp:latest</image>
                  </to>
                  <container>
                    <entrypoint>
                      <shell>bash</shell>
                      <option>-c</option>
                      <arg>/entrypoint.sh</arg>
                    </entrypoint>
                    <ports>
                      <port>9000</port>
                    </ports>
                    <environment>
                      <SPRING_OUTPUT_ANSI_ENABLED>ALWAYS</SPRING_OUTPUT_ANSI_ENABLED>
                      <JHIPSTER_SLEEP>0</JHIPSTER_SLEEP>
                    </environment>
                    <creationTime>USE_CURRENT_TIMESTAMP</creationTime>
                    <user>1000</user>
                  </container>
                  <extraDirectories>
                    <paths>src/main/docker/jib</paths>
                    <permissions>
                      <permission>
                        <file>/entrypoint.sh</file>
                        <mode>755</mode>
                      </permission>
                    </permissions>
                  </extraDirectories>
                </configuration>
              </plugin>
        """
      )
      .and()
      .hasFile("src/main/docker/jib/entrypoint.sh")
      .containing("\"com.jhipster.test.MyappApp\"");
  }

  @Test
  void shouldBuildDockerFileModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .put("serverPort", 9000)
      .build();

    JHipsterModule module = factory.buildDockerFileModule(properties);

    assertThatModule(module).hasFile("Dockerfile").containing("EXPOSE 9000");
  }
}
