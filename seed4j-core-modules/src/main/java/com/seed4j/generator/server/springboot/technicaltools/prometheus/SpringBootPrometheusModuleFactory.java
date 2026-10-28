package com.seed4j.generator.server.springboot.technicaltools.prometheus;

import static com.seed4j.generator.server.springboot.common.domain.SpringBootProperties.SPRING_MAIN;
import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

class SpringBootPrometheusModuleFactory {

  private static final GroupId MICROMETER_GROUP = groupId("io.micrometer");

  Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(MICROMETER_GROUP, artifactId("micrometer-registry-prometheus"))
        .and()
      .applicationProperties(SPRING_MAIN)
        .set(
          propertyKey("management.endpoints.web.exposure.include"),
          propertyValue("configprops", "env", "health", "info", "logfile", "loggers", "prometheus", "threaddump")
        )
        .set(propertyKey("management.endpoint.prometheus.access"), propertyValue("read-only"))
        .set(propertyKey("management.prometheus.metrics.export.enabled"), propertyValue(true))
        .and()
      .build();
    // @formatter:on
  }
}
