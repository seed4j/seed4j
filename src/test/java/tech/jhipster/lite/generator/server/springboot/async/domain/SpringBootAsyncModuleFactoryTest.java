package tech.jhipster.lite.generator.server.springboot.async.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class SpringBootAsyncModuleFactoryTest {

  private static final SpringBootAsyncModuleFactory factory = new SpringBootAsyncModuleFactory();

  @Test
  void shouldCreateModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasJavaSources("com/jhipster/test/technical/infrastructure/secondary/async/AsyncConfiguration.java")
      .hasFile("src/main/resources/config/application.properties")
      .containing("spring.task.execution.pool.keep-alive=10s")
      .containing("spring.task.execution.pool.max-size=16")
      .containing("spring.task.execution.pool.queue-capacity=100")
      .containing("spring.task.execution.thread-name-prefix=myapp-task-")
      .containing("spring.task.scheduling.pool.size=2")
      .containing("spring.task.scheduling.thread-name-prefix=myapp-scheduling-");
  }
}
