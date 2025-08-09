package com.seed4j.generator.server.springboot.springcloud.eureka.domain;

import static com.seed4j.generator.server.springboot.springcloud.common.domain.SpringCloudModuleDependencies.SPRING_CLOUD_GROUP;
import static com.seed4j.generator.server.springboot.springcloud.common.domain.SpringCloudModuleDependencies.springCloudDependenciesManagement;
import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.dockerComposeFile;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.propertyKey;
import static com.seed4j.module.domain.JHipsterModule.propertyValue;
import static com.seed4j.module.domain.JHipsterModule.to;
import static com.seed4j.module.domain.JHipsterModule.versionSlug;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.javaproperties.PropertyValue;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.base64.domain.Base64Utils;
import com.seed4j.shared.error.domain.Assert;
import java.util.Locale;

public class EurekaModuleFactory {

  private static final String JWT_BASE_64_SECRET = "jwtBase64Secret";
  private static final PropertyValue TRUE_VALUE = propertyValue(true);
  private static final PropertyValue FALSE_VALUE = propertyValue(false);
  private static final SeedSource SPRING_CLOUD_SOURCE = from("server/springboot/springcloud/configclient");
  private static final SeedSource EUREKA_SOURCE = from("server/springboot/springcloud/eureka");

  private final DockerImages dockerImages;

  public EurekaModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String jwtBase64secret = properties.getOrDefaultString(JWT_BASE_64_SECRET, Base64Utils.getBase64Secret());
    String baseName = properties.projectBaseName().get();
    String lowerCaseBaseName = baseName.toLowerCase(Locale.ROOT);

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("jhipsterRegistryDockerImage", dockerImages.get("jhipster/jhipster-registry").fullName())
        .put("base64JwtSecret", jwtBase64secret)
        .and()
      .javaDependencies()
        .addDependencyManagement(springCloudDependenciesManagement())
        .addDependency(SPRING_CLOUD_GROUP, artifactId("spring-cloud-starter-bootstrap"))
        .addDependency(
          SPRING_CLOUD_GROUP,
          artifactId("spring-cloud-starter-netflix-eureka-client"),
          versionSlug("spring-cloud-netflix-eureka-client")
        )
        .and()
      .files()
        .add(SPRING_CLOUD_SOURCE.template("jhipster-registry.yml"), to("src/main/docker/jhipster-registry.yml"))
        .add(
            EUREKA_SOURCE.template("application.config.properties"),
            to("src/main/docker/central-server-config/localhost-config/application.properties")
        )
        .and()
      .springMainBootstrapProperties()
        .set(propertyKey("spring.application.name"), propertyValue(baseName))
        .set(propertyKey("spring.cloud.compatibility-verifier.enabled"), FALSE_VALUE)
        .set(propertyKey("eureka.client.service-url.defaultZone"), propertyValue("http://admin:admin@localhost:8761/eureka"))
        .set(propertyKey("eureka.client.enabled"), TRUE_VALUE)
        .set(propertyKey("eureka.client.healthcheck.enabled"), TRUE_VALUE)
        .set(propertyKey("eureka.client.fetch-registry"), TRUE_VALUE)
        .set(propertyKey("eureka.client.register-with-eureka"), TRUE_VALUE)
        .set(propertyKey("eureka.client.instance-info-replication-interval-seconds"), propertyValue(10))
        .set(propertyKey("eureka.client.registry-fetch-interval-seconds"), propertyValue(10))
        .set(propertyKey("eureka.instance.appname"), propertyValue(lowerCaseBaseName))
        .set(propertyKey("eureka.instance.instance-id"), propertyValue(instanceId(lowerCaseBaseName)))
        .set(propertyKey("eureka.instance.lease-renewal-interval-in-seconds"), propertyValue(5))
        .set(propertyKey("eureka.instance.lease-expiration-duration-in-seconds"), propertyValue(10))
        .set(propertyKey("eureka.instance.status-page-url-path"), propertyValue("${management.endpoints.web.base-path}/info"))
        .set(propertyKey("eureka.instance.health-check-url-path"), propertyValue("${management.endpoints.web.base-path}/health"))
        .and()
      .springTestBootstrapProperties()
        .set(propertyKey("spring.application.name"), propertyValue(baseName))
        .set(propertyKey("eureka.client.enabled"), FALSE_VALUE)
        .set(propertyKey("spring.cloud.compatibility-verifier.enabled"), FALSE_VALUE)
        .and()
      .dockerComposeFile()
        .append(dockerComposeFile("src/main/docker/jhipster-registry.yml"))
        .and()
      .build();
    // @formatter:on
  }

  private String instanceId(String lowerCaseBaseName) {
    return lowerCaseBaseName + ":${spring.application.instance-id:${random.value}}";
  }
}
