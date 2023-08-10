package tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class SpringBootActuatorModuleFactoryTest {

  private static final SpringBootActuatorModuleFactory factory = new SpringBootActuatorModuleFactory();

  @Test
  void shouldCreateSpringBootActuatorModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModule(properties);
    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing("<groupId>org.springframework.boot</groupId>")
      .containing("<artifactId>spring-boot-starter-actuator</artifactId>")
      .and()
      .hasFile("src/main/resources/config/application.properties")
      .containing("management.endpoints.web.base-path=/management")
      .containing("management.endpoints.web.exposure.include=configprops,env,health,info,logfile,loggers,threaddump")
      .containing("management.endpoint.health.probes.enabled=true")
      .containing("management.endpoint.health.show-details=always");
  }
}
