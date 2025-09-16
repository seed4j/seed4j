package com.seed4j.generator.server.springboot.technicaltools.actuator.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringBootActuatorModuleFactoryTest {

  private static final SpringBootActuatorModuleFactory factory = new SpringBootActuatorModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildModule(properties);
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
