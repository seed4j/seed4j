package com.seed4j.generator.server.micronaut.management.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class MicronautManagementModuleFactory {

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .javaDependencies()
      .addDependency(groupId("io.micronaut"), artifactId("micronaut-management"))
      .and()
      .springMainProperties()
      .set(propertyKey("endpoints.all.enabled"), propertyValue(true))
      .set(propertyKey("endpoints.all.sensitive"), propertyValue(false))
      .set(propertyKey("endpoints.health.enabled"), propertyValue(true))
      .set(propertyKey("endpoints.health.sensitive"), propertyValue(false))
      .set(propertyKey("endpoints.info.enabled"), propertyValue(true))
      .set(propertyKey("endpoints.info.sensitive"), propertyValue(false))
      .and()
      .build();
  }
}
