package com.seed4j.generator.server.micronaut.openapi.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class MicronautOpenApiModuleFactory {

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .javaDependencies()
      .addDependency(groupId("io.micronaut.openapi"), artifactId("micronaut-openapi"))
      .addDependency(groupId("io.swagger.core.v3"), artifactId("swagger-annotations"))
      .and()
      .springMainProperties()
      .set(propertyKey("micronaut.router.static-resources.swagger.paths"), propertyValue("classpath:META-INF/swagger"))
      .set(propertyKey("micronaut.router.static-resources.swagger.mapping"), propertyValue("/swagger/**"))
      .set(propertyKey("micronaut.router.static-resources.swagger-ui.paths"), propertyValue("classpath:META-INF/swagger/views/swagger-ui"))
      .set(propertyKey("micronaut.router.static-resources.swagger-ui.mapping"), propertyValue("/swagger-ui/**"))
      .and()
      .build();
  }
}
