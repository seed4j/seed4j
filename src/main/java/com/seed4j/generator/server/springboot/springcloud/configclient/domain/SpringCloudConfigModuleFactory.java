package com.seed4j.generator.server.springboot.springcloud.configclient.domain;

import static com.seed4j.generator.server.springboot.springcloud.common.domain.SpringCloudModuleDependencies.SPRING_CLOUD_GROUP;
import static com.seed4j.generator.server.springboot.springcloud.common.domain.SpringCloudModuleDependencies.springCloudDependenciesManagement;
import static com.seed4j.module.domain.SeedModule.SeedModuleBuilder;
import static com.seed4j.module.domain.SeedModule.artifactId;
import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.propertyKey;
import static com.seed4j.module.domain.SeedModule.propertyValue;
import static com.seed4j.module.domain.SeedModule.springProfile;
import static com.seed4j.module.domain.SeedModule.toSrcMainDocker;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.javaproperties.PropertyValue;
import com.seed4j.module.domain.javaproperties.SeedModuleSpringProperties.SeedModuleSpringPropertiesBuilder;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.base64.domain.Base64Utils;
import com.seed4j.shared.error.domain.Assert;

public class SpringCloudConfigModuleFactory {

  private static final String JWT_BASE_64_SECRET = "jwtBase64Secret";

  private static final SeedSource SOURCE = from("server/springboot/springcloud/configclient");

  private static final PropertyValue FALSE_VALUE = propertyValue(false);

  private final DockerImages dockerImages;

  public SpringCloudConfigModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    PropertyValue baseNameValue = propertyValue(properties.projectBaseName().get());

    SeedModuleBuilder builder = initBuilder(properties);

    appendCommonProperties(builder.springMainBootstrapProperties(), baseNameValue);
    appendCommonProperties(builder.springMainBootstrapProperties(springProfile("local")), baseNameValue);

    // @formatter:off
    return builder
      .springMainBootstrapProperties()
        .set(propertyKey("spring.cloud.config.fail-fast"), propertyValue(true))
        .and()
      .springMainBootstrapProperties(springProfile("local"))
        .set(propertyKey("spring.cloud.config.fail-fast"), FALSE_VALUE)
        .and()
      .springTestBootstrapProperties()
        .set(propertyKey("spring.application.name"), baseNameValue)
        .set(propertyKey("spring.cloud.config.enabled"), FALSE_VALUE)
        .and()
      .build();
    // @formatter:on
  }

  private SeedModuleBuilder initBuilder(SeedModuleProperties properties) {
    String jwtBase64secret = properties.getOrDefaultString(JWT_BASE_64_SECRET, Base64Utils.getBase64Secret());

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("jhipsterRegistryDockerImage", dockerImages.get("jhipster/jhipster-registry").fullName())
        .put("base64JwtSecret", jwtBase64secret)
        .and()
      .javaDependencies()
        .addDependencyManagement(springCloudDependenciesManagement())
        .addDependency(SPRING_CLOUD_GROUP, artifactId("spring-cloud-starter-bootstrap"))
        .addDependency(SPRING_CLOUD_GROUP, artifactId("spring-cloud-starter-config"))
        .and()
      .files()
        .add(SOURCE.template("jhipster-registry.yml"), toSrcMainDocker().append("jhipster-registry.yml"))
        .add(
          SOURCE.template("application.config.properties"),
          toSrcMainDocker().append("central-server-config/localhost-config/application.properties")
        )
      .and();
    // @formatter:on
  }

  private void appendCommonProperties(SeedModuleSpringPropertiesBuilder builder, PropertyValue baseNameValue) {
    builder
      .set(propertyKey("spring.application.name"), baseNameValue)
      .set(propertyKey("jhipster.registry.password"), propertyValue("admin"))
      .set(propertyKey("spring.cloud.compatibility-verifier.enabled"), FALSE_VALUE)
      .set(propertyKey("spring.cloud.config.label"), propertyValue("main"))
      .set(propertyKey("spring.cloud.config.name"), baseNameValue)
      .set(propertyKey("spring.cloud.config.retry.initial-interval"), propertyValue(1000))
      .set(propertyKey("spring.cloud.config.retry.max-attempts"), propertyValue(100))
      .set(propertyKey("spring.cloud.config.retry.max-interval"), propertyValue(2000))
      .set(propertyKey("spring.cloud.config.uri"), propertyValue("http://admin:${jhipster.registry.password}@localhost:8761/config"));
  }
}
