package tech.jhipster.lite.generator.server.springboot.springcloud.consul.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.generator.base64.domain.Base64Utils;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.LogLevel;
import tech.jhipster.lite.module.domain.docker.DockerImageVersion;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyType;
import tech.jhipster.lite.module.domain.javaproperties.PropertyValue;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class ConsulModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/springcloud/consul");

  private static final GroupId SPRING_CLOUD_GROUP_ID = groupId("org.springframework.cloud");
  private static final PropertyValue FALSE_VALUE = propertyValue(false);
  private static final PropertyValue TRUE_VALUE = propertyValue(true);
  private static final String DOCKER_IMAGE_CONSUL = "consul";
  private static final String DOCKER_IMAGE_CONFIG_LOADER = "jhipster/consul-config-loader";
  private static final String DOCKER_COMPOSE_COMMAND = "docker compose -f src/main/docker/consul.yml up -d";

  private final DockerImages dockerImages;

  public ConsulModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String baseName = properties.projectBaseName().get();
    DockerImageVersion dockerImageConsul = dockerImages.get(DOCKER_IMAGE_CONSUL);
    DockerImageVersion dockerImageConfigLoader = dockerImages.get(DOCKER_IMAGE_CONFIG_LOADER);

    //@formatter:off
    JHipsterModuleBuilder builder = moduleBuilder(properties)
      .context()
        .put("dockerImageConsul", dockerImageConsul.fullName())
        .put("dockerImageConfigLoader", dockerImageConfigLoader.fullName())
        .put("base64JwtSecret", Base64Utils.getBase64Secret())
        .and()
      .files()
        .add(SOURCE.append("src").template("consul.yml"), toSrcMainDocker().append("consul.yml"))
        .add(SOURCE.append("docker").template("application.config.yml.mustache"), toSrcMainDocker().append("central-server-config").append("application.yml"))
        .and()
      .javaDependencies()
        .addDependencyManagement(springCloudDependencyManagement())
        .addDependency(SPRING_CLOUD_GROUP_ID, artifactId("spring-cloud-starter-bootstrap"))
        .addDependency(SPRING_CLOUD_GROUP_ID, artifactId("spring-cloud-starter-consul-discovery"))
        .addDependency(SPRING_CLOUD_GROUP_ID, artifactId("spring-cloud-starter-consul-config"))
        .and()
      .startupCommand(DOCKER_COMPOSE_COMMAND)
      .springMainBootstrapProperties()
        .set(propertyKey("spring.application.name"), propertyValue(baseName))
        .set(propertyKey("spring.cloud.consul.host"), propertyValue("localhost"))
        .set(propertyKey("spring.cloud.consul.port"), propertyValue("8500"))
        .set(propertyKey("spring.cloud.compatibility-verifier.enabled"), FALSE_VALUE)
        .set(propertyKey("spring.cloud.consul.config.format"), propertyValue("yaml"))
        .set(propertyKey("spring.cloud.consul.config.profile-separator"), propertyValue("-"))
        .set(propertyKey("spring.cloud.consul.config.watch.enabled"), FALSE_VALUE)
        .set(propertyKey("spring.cloud.consul.discovery.health-check-path"), propertyValue("${server.servlet.context-path:}/management/health"))
        .set(propertyKey("spring.cloud.consul.discovery.tags[0]"), propertyValue("version=@project.version@"))
        .set(propertyKey("spring.cloud.consul.discovery.tags[1]"), propertyValue("context-path=${server.servlet.context-path:}"))
        .set(propertyKey("spring.cloud.consul.discovery.tags[2]"), propertyValue("profile=${spring.profiles.active:}"))
        .set(propertyKey("spring.cloud.consul.discovery.tags[3]"), propertyValue("git-version=${git.commit.id.describe:}"))
        .set(propertyKey("spring.cloud.consul.discovery.tags[4]"), propertyValue("git-commit=${git.commit.id.abbrev:}"))
        .set(propertyKey("spring.cloud.consul.discovery.tags[5]"), propertyValue("git-branch=${git.branch:}"))
        .set(propertyKey("spring.cloud.consul.discovery.instance-id"), propertyValue(baseName + ":${spring.application.instance-id:${random.value}}"))
        .set(propertyKey("spring.cloud.consul.discovery.service-name"), propertyValue(baseName))
        .set(propertyKey("spring.cloud.consul.discovery.prefer-ip-address"), TRUE_VALUE)
        .and()
      .springTestBootstrapProperties()
        .set(propertyKey("spring.cloud.consul.enabled"), FALSE_VALUE)
        .set(propertyKey("spring.cloud.compatibility-verifier.enabled"), FALSE_VALUE)
        .and()
      .springMainLogger("org.apache", LogLevel.ERROR)
      .springTestLogger("org.apache", LogLevel.ERROR);
    //@formatter:on

    return builder.build();
  }

  private static JavaDependency springCloudDependencyManagement() {
    return javaDependency()
      .groupId("org.springframework.cloud")
      .artifactId("spring-cloud-dependencies")
      .versionSlug("spring-cloud.version")
      .type(JavaDependencyType.POM)
      .scope(JavaDependencyScope.IMPORT)
      .build();
  }
}
