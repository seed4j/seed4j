package com.seed4j.generator.server.micronaut.openapi;

import static com.seed4j.generator.server.micronaut.common.domain.MicronautProperties.MICRONAUT_MAIN;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.javaAnnotationProcessor;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.to;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

class MicronautOpenApiModuleFactory {

  private static final String MICRONAUT_OPENAPI_GROUP = "io.micronaut.openapi";

  Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .javaDependencies()
      .addDependency(micronautOpenApiAnnotations())
      .and()
      .javaCompiler()
      .addAnnotationProcessor(javaAnnotationProcessor().groupId(MICRONAUT_OPENAPI_GROUP).artifactId("micronaut-openapi").build())
      .and()
      .files()
      .add(from("server/micronaut/openapi").file("openapi.properties"), to("openapi.properties"))
      .and()
      .applicationProperties(MICRONAUT_MAIN)
      .set(propertyKey("micronaut.router.static-resources.swagger.paths"), propertyValue("classpath:META-INF/swagger"))
      .set(propertyKey("micronaut.router.static-resources.swagger.mapping"), propertyValue("/swagger/**"))
      .set(propertyKey("micronaut.router.static-resources.swagger-ui.paths"), propertyValue("classpath:META-INF/swagger/views/swagger-ui"))
      .set(propertyKey("micronaut.router.static-resources.swagger-ui.mapping"), propertyValue("/swagger-ui/**"))
      .and()
      .build();
  }

  private static JavaDependency micronautOpenApiAnnotations() {
    return JavaDependency.builder()
      .groupId(MICRONAUT_OPENAPI_GROUP)
      .artifactId("micronaut-openapi-annotations")
      .scope(JavaDependencyScope.PROVIDED)
      .build();
  }
}
