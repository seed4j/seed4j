package tech.jhipster.lite.generator.server.javatool.jacoco.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class JacocoThresholdModuleFactoryTest {

  private static final JacocoThresholdModuleFactory factory = new JacocoThresholdModuleFactory();

  @Test
  @EnabledOnOs(OS.LINUX)
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
                    <execution>
                      <id>post-merge-report</id>
                      <phase>verify</phase>
                      <goals>
                        <goal>report</goal>
                      </goals>
                      <configuration>
                        <dataFile>target/jacoco/allTest.exec</dataFile>
                        <outputDirectory>target/jacoco/</outputDirectory>
                      </configuration>
                    </execution>
                    <execution>
                      <id>check</id>
                      <goals>
                        <goal>check</goal>
                      </goals>
                      <configuration>
                        <dataFile>target/jacoco/allTest.exec</dataFile>
                        <rules>
                          <rule>
                            <element>CLASS</element>
                            <limits>
                              <limit>
                                <counter>LINE</counter>
                                <value>COVEREDRATIO</value>
                                <minimum>1.00</minimum>
                              </limit>
                              <limit>
                                <counter>BRANCH</counter>
                                <value>COVEREDRATIO</value>
                                <minimum>1.00</minimum>
                              </limit>
                            </limits>
                          </rule>
                        </rules>
                      </configuration>
                    </execution>
                  </executions>
                </plugin>
        """
      );
  }
}
