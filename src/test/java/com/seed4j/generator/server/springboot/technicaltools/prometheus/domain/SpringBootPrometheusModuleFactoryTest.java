package com.seed4j.generator.server.springboot.technicaltools.prometheus.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringBootPrometheusModuleFactoryTest {

  private static final SpringBootPrometheusModuleFactory factory = new SpringBootPrometheusModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildModule(properties);
    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing("<groupId>io.micrometer</groupId>")
      .containing("<artifactId>micrometer-registry-prometheus</artifactId>")
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        management:
          endpoint:
            prometheus:
              access: read-only
          endpoints:
            web:
              exposure:
                include:
                - configprops
                - env
                - health
                - info
                - logfile
                - loggers
                - prometheus
                - threaddump
          prometheus:
            metrics:
              export:
                enabled: true
        """
      );
  }
}
