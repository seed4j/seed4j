package com.seed4j.generator.server.springboot.technicaltools.actuator.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SpringBootActuatorModuleFactory {

  private static final GroupId SPRING_GROUP = groupId("org.springframework.boot");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(SPRING_GROUP, artifactId("spring-boot-starter-actuator"))
        .and()
      .springMainProperties()
        .set(propertyKey("management.endpoints.web.base-path"), propertyValue("/management"))
        .set(
          propertyKey("management.endpoints.web.exposure.include"),
          propertyValue("configprops", "env", "health", "info", "logfile", "loggers", "threaddump")
        )
        .set(propertyKey("management.endpoint.health.probes.enabled"), propertyValue(true))
        .set(propertyKey("management.endpoint.health.show-details"), propertyValue("always"))
        .set(propertyKey("management.endpoint.health.access"), propertyValue("read-only"))
        .set(propertyKey("management.endpoints.access.default"), propertyValue("none"))
        .set(propertyKey("management.endpoints.jmx.exposure.exclude"), propertyValue("*"))
        .and()
      .springLocalProperties()
        .set(propertyKey("management.endpoints.web.exposure.include"), propertyValue("*"))
        .set(propertyKey("management.endpoints.access.default"), propertyValue("unrestricted"))
        .and()
      .build();
    // @formatter:on
  }
}
