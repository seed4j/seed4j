package com.seed4j.generator.server.springboot.async.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SpringBootAsyncModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/async/src");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    Seed4JDestination mainDestination = toSrcMainJava().append(properties.packagePath()).append("wire/async/infrastructure/secondary");
    String baseName = properties.projectBaseName().get();

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.template("AsyncConfiguration.java"), mainDestination.append("AsyncConfiguration.java"))
        .and()
      .springMainProperties()
        .set(propertyKey("spring.task.execution.pool.keep-alive"), propertyValue("10s"))
        .set(propertyKey("spring.task.execution.pool.max-size"), propertyValue(16))
        .set(propertyKey("spring.task.execution.pool.queue-capacity"), propertyValue(100))
        .set(propertyKey("spring.task.execution.thread-name-prefix"), propertyValue(baseName + "-task-"))
        .set(propertyKey("spring.task.scheduling.pool.size"), propertyValue(2))
        .set(propertyKey("spring.task.scheduling.thread-name-prefix"), propertyValue(baseName + "-scheduling-"))
        .and()
      .build();
    // @formatter:on
  }
}
