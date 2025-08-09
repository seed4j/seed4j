package com.seed4j.generator.server.springboot.springcloud.gateway.domain;

import static com.seed4j.generator.server.springboot.springcloud.common.domain.SpringCloudModuleDependencies.SPRING_CLOUD_GROUP;
import static com.seed4j.generator.server.springboot.springcloud.common.domain.SpringCloudModuleDependencies.springCloudDependenciesManagement;
import static com.seed4j.module.domain.SeedModule.artifactId;
import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.propertyKey;
import static com.seed4j.module.domain.SeedModule.propertyValue;
import static com.seed4j.module.domain.SeedModule.toSrcMainJava;
import static com.seed4j.module.domain.SeedModule.toSrcTestJava;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.javaproperties.PropertyValue;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class GatewayModuleFactory {

  private static final String GATEWAY_PACKAGE = "wire/gateway/infrastructure/primary";
  private static final SeedSource SOURCE = from("server/springboot/springcloud/gateway/java");
  private static final PropertyValue TRUE_VALUE = propertyValue(true);

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    SeedDestination testDestination = toSrcTestJava().append(packagePath).append(GATEWAY_PACKAGE);
    SeedDestination destination = toSrcMainJava().append(packagePath).append(GATEWAY_PACKAGE);

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependencyManagement(springCloudDependenciesManagement())
        .addDependency(SPRING_CLOUD_GROUP, artifactId("spring-cloud-starter-bootstrap"))
        .addDependency(SPRING_CLOUD_GROUP, artifactId("spring-cloud-starter-gateway"))
        .and()
      .springMainBootstrapProperties()
        .set(propertyKey("spring.application.name"), propertyValue(properties.projectBaseName().get()))
        .set(propertyKey("spring.cloud.gateway.discovery.locator.enabled"), TRUE_VALUE)
        .set(propertyKey("spring.cloud.gateway.discovery.locator.lower-case-service-id"), TRUE_VALUE)
        .set(propertyKey("spring.cloud.gateway.discovery.locator.predicates[0].name"), propertyValue("Path"))
        .set(
          propertyKey("spring.cloud.gateway.discovery.locator.predicates[0].args[pattern]"),
          propertyValue("'/services/'+serviceId.toLowerCase()+'/**'")
        )
        .set(propertyKey("spring.cloud.gateway.discovery.locator.filters[0].name"), propertyValue("RewritePath"))
        .set(
          propertyKey("spring.cloud.gateway.discovery.locator.filters[0].args[regexp]"),
          propertyValue("'/services/' + serviceId.toLowerCase() + '/(?<remaining>.*)'")
        )
        .set(propertyKey("spring.cloud.gateway.discovery.locator.filters[0].args[replacement]"), propertyValue("'/${remaining}'"))
        .and()
      .springTestBootstrapProperties()
        .set(propertyKey("spring.application.name"), propertyValue(properties.projectBaseName().get()))
        .set(propertyKey("spring.cloud.gateway.discovery.locator.enabled"), propertyValue(false))
        .and()
      .files()
        .add(SOURCE.template("GatewayResource.java"), destination.append("GatewayResource.java"))
        .add(SOURCE.template("RouteVM.java"), destination.append("vm/RouteVM.java"))
        .add(SOURCE.template("test/GatewayResourceIT.java"), testDestination.append("GatewayResourceIT.java"))
        .and()
      .build();
    // @formatter:on
  }
}
