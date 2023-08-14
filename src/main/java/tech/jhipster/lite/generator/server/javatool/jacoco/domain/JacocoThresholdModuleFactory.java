package tech.jhipster.lite.generator.server.javatool.jacoco.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class JacocoThresholdModuleFactory {

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .mandatoryReplacements()
      .in(path("pom.xml"))
      .add(
        lineAfterRegex(
          "<outputDirectory>target\\/jacoco\\/<\\/outputDirectory>[\n\r]*\\s*<\\/configuration>[\n\r]*\\s*<\\/execution>\\s*$"
        ),
        """
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
                        </execution>\
            """
      )
      .and()
      .and()
      .build();
  }
}
