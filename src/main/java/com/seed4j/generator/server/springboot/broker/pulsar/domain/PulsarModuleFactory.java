package com.seed4j.generator.server.springboot.broker.pulsar.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.javaDependency;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainDocker;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class PulsarModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/broker/pulsar");

  private static final String PULSAR_CONFIG = "wire/pulsar/infrastructure/config";

  private final DockerImages dockerImages;

  public PulsarModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    Seed4JDestination testDestination = toSrcTestJava().append(packagePath);

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("pulsarDockerImage", dockerImages.get("apachepulsar/pulsar").fullName())
        .and()
      .javaDependencies()
        .addDependency(groupId("org.springframework.boot"), artifactId("spring-boot-starter-pulsar"))
        .addDependency(testContainerDependency())
        .and()
      .files()
        .add(SOURCE.template("pulsar.yml"), toSrcMainDocker().append("pulsar.yml"))
        .add(SOURCE.template("PulsarTestContainerExtension.java"), testDestination.append("PulsarTestContainerExtension.java"))
        .batch(SOURCE, toSrcMainJava().append(packagePath).append(PULSAR_CONFIG))
          .addTemplate("PulsarProperties.java")
          .addTemplate("PulsarConfiguration.java")
          .and()
        .add(SOURCE.template("PulsarConfigurationIT.java"), testDestination.append(PULSAR_CONFIG).append("PulsarConfigurationIT.java"))
        .and()
      .startupCommands()
        .dockerCompose("src/main/docker/pulsar.yml")
        .and()
      .springMainProperties()
        .set(propertyKey("pulsar.client.service-url"), propertyValue("pulsar://localhost:6650"))
        .and()
      .springTestProperties()
        .set(propertyKey("pulsar.client.num-io-threads"), propertyValue(8))
        .set(propertyKey("pulsar.producer.topic-name"), propertyValue("test-topic"))
        .set(propertyKey("pulsar.consumer.topic-names[0]"), propertyValue("test-topic"))
        .set(propertyKey("pulsar.consumer.subscription-name"), propertyValue("test-subscription"))
        .and()
      .integrationTestExtension("PulsarTestContainerExtension")
      .build();
    // @formatter:on
  }

  private JavaDependency testContainerDependency() {
    return javaDependency()
      .groupId("org.testcontainers")
      .artifactId("pulsar")
      .versionSlug("testcontainers")
      .scope(JavaDependencyScope.TEST)
      .build();
  }
}
