package com.seed4j.generator.server.springboot.async.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModule;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringBootAsyncModuleFactoryTest {

  private static final SpringBootAsyncModuleFactory factory = new SpringBootAsyncModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasJavaSources("com/seed4j/growth/wire/async/infrastructure/secondary/AsyncConfiguration.java")
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        spring:
          task:
            execution:
              pool:
                keep-alive: 10s
                max-size: 16
                queue-capacity: 100
              thread-name-prefix: myapp-task-
            scheduling:
              pool:
                size: 2
              thread-name-prefix: myapp-scheduling-
        """
      );
  }
}
