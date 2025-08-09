package com.seed4j.generator.server.springboot.async.domain;

import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.propertyKey;
import static com.seed4j.module.domain.SeedModule.propertyValue;
import static com.seed4j.module.domain.SeedModule.toSrcMainJava;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SpringBootAsyncModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/async/src");

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    SeedDestination mainDestination = toSrcMainJava().append(properties.packagePath()).append("wire/async/infrastructure/secondary");
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
