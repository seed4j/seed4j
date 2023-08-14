package tech.jhipster.lite.generator.server.springboot.async.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class SpringBootAsyncModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/async/src");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    JHipsterDestination mainDestination = toSrcMainJava().append(properties.packagePath()).append("wire/async/infrastructure/secondary");
    String baseName = properties.projectBaseName().get();

    //@formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.template("AsyncConfiguration.java"), mainDestination.append("AsyncConfiguration.java"))
        .and()
      .springMainProperties()
        .set(propertyKey("spring.task.execution.pool.keep-alive"), propertyValue("10s"))
        .set(propertyKey("spring.task.execution.pool.max-size"), propertyValue("16"))
        .set(propertyKey("spring.task.execution.pool.queue-capacity"), propertyValue("100"))
        .set(propertyKey("spring.task.execution.thread-name-prefix"), propertyValue(baseName + "-task-"))
        .set(propertyKey("spring.task.scheduling.pool.size"), propertyValue("2"))
        .set(propertyKey("spring.task.scheduling.thread-name-prefix"), propertyValue(baseName + "-scheduling-"))
        .and()
      .build();
    //@formatter:on
  }
}
