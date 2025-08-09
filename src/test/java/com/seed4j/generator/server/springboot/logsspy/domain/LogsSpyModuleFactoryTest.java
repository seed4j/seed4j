package com.seed4j.generator.server.springboot.logsspy.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class LogsSpyModuleFactoryTest {

  private static final LogsSpyModuleFactory factory = new LogsSpyModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    SeedModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFiles("documentation/logs-spy.md")
      .hasJavaTests("com/seed4j/growth/Logs.java", "com/seed4j/growth/LogsSpy.java", "com/seed4j/growth/LogsSpyExtension.java");
  }
}
