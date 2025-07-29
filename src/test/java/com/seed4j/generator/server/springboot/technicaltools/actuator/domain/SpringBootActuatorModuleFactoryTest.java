package com.seed4j.generator.server.springboot.technicaltools.actuator.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringBootActuatorModuleFactoryTest {

  private static final SpringBootActuatorModuleFactory factory = new SpringBootActuatorModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModule(properties);
    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing("<groupId>org.springframework.boot</groupId>")
      .containing("<artifactId>spring-boot-starter-actuator</artifactId>")
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        management:
          endpoint:
            health:
              access: read-only
              probes:
                enabled: true
              show-details: always
          endpoints:
            access:
              default: none
            jmx:
              exposure:
                exclude: '*'
            web:
              base-path: /management
              exposure:
                include:
                - configprops
                - env
                - health
                - info
                - logfile
                - loggers
                - threaddump
        """
      )
      .and()
      .hasFile("src/main/resources/config/application-local.yml")
      .containing(
        """
        management:
          endpoints:
            access:
              default: unrestricted
            web:
              exposure:
                include: '*'
        """
      );
  }
}
