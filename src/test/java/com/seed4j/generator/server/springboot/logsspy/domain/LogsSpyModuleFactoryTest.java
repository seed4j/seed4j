package com.seed4j.generator.server.springboot.logsspy.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class LogsSpyModuleFactoryTest {

  private static final LogsSpyModuleFactory factory = new LogsSpyModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFiles("documentation/logs-spy.md")
      .hasJavaTests("com/seed4j/growth/Logs.java", "com/seed4j/growth/LogsSpy.java", "com/seed4j/growth/LogsSpyExtension.java");
  }
}
