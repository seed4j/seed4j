package com.seed4j.generator.server.springboot.logsspy.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class LogsSpyModuleFactoryTest {

  private static final LogsSpyModuleFactory factory = new LogsSpyModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFiles("documentation/logs-spy.md")
      .hasJavaTests(
        "tech/jhipster/jhlitest/Logs.java",
        "tech/jhipster/jhlitest/LogsSpy.java",
        "tech/jhipster/jhlitest/LogsSpyExtension.java"
      );
  }
}
