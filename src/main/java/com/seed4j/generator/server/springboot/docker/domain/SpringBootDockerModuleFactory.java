package com.seed4j.generator.server.springboot.docker.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.gradleCommunityPlugin;
import static com.seed4j.module.domain.Seed4JModule.mavenPlugin;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.to;
import static com.seed4j.module.domain.javadependency.JavaDependencyScope.RUNTIME;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.gradleplugin.GradleMainBuildPlugin;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.mavenplugin.MavenPluginConfiguration;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SpringBootDockerModuleFactory {

  private static final String PROPERTIES_FIELD = "properties";
  private static final Seed4JSource SOURCE = from("server/springboot/docker");
  private static final Seed4JSource JIB_SOURCE = SOURCE.append("jib");
  private static final String JAVA_DOCKER_IMAGE = "eclipse-temurin:%s-jre-jammy";

  public Seed4JModule buildJibModule(Seed4JModuleProperties properties) {
    Assert.notNull(PROPERTIES_FIELD, properties);

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("mainClass", mainClassName(properties))
        .and()
      .mavenPlugins()
        .plugin(mavenJibPlugin(properties))
        .and()
      .gradlePlugins()
        .plugin(gradleJibPlugin(properties))
        .and()
      .files()
        .add(JIB_SOURCE.template("entrypoint.sh"), to("src/main/docker/jib").append("entrypoint.sh"))
        .and()
      .build();
    // @formatter:on
  }

  private String mainClassName(Seed4JModuleProperties properties) {
    return "%s.%sApp".formatted(properties.basePackage().get(), properties.projectBaseName().capitalized());
  }

  private MavenPlugin mavenJibPlugin(Seed4JModuleProperties properties) {
    return mavenPlugin()
      .groupId("com.google.cloud.tools")
      .artifactId("jib-maven-plugin")
      .versionSlug("jib-maven-plugin")
      .configuration(jibPluginConfiguration(properties))
      .build();
  }

  private String dockerBaseImage(Seed4JModuleProperties properties) {
    return JAVA_DOCKER_IMAGE.formatted(properties.javaVersion().get());
  }

  private MavenPluginConfiguration jibPluginConfiguration(Seed4JModuleProperties properties) {
    return new MavenPluginConfiguration(
      """
        <from>
          <image>%s</image>
          <platforms>
            <platform>
              <architecture>amd64</architecture>
              <os>linux</os>
            </platform>
          </platforms>
        </from>
        <to>
          <image>%s:latest</image>
        </to>
        <container>
          <entrypoint>
            <shell>bash</shell>
            <option>-c</option>
            <arg>/entrypoint.sh</arg>
          </entrypoint>
          <ports>
            <port>%s</port>
          </ports>
          <environment>
            <SPRING_OUTPUT_ANSI_ENABLED>ALWAYS</SPRING_OUTPUT_ANSI_ENABLED>
            <SEED4J_SLEEP>0</SEED4J_SLEEP>
          </environment>
          <creationTime>USE_CURRENT_TIMESTAMP</creationTime>
          <user>1000</user>
        </container>
        <extraDirectories>
          <paths>src/main/docker/jib</paths>
          <permissions>
            <permission>
              <file>/entrypoint.sh</file>
              <mode>755</mode>
            </permission>
          </permissions>
        </extraDirectories>
      """.formatted(dockerBaseImage(properties), properties.projectBaseName().get(), properties.serverPort().get())
    );
  }

  private GradleMainBuildPlugin gradleJibPlugin(Seed4JModuleProperties properties) {
    return gradleCommunityPlugin()
      .id("com.google.cloud.tools.jib")
      .pluginSlug("jib")
      .versionSlug("jib")
      .configuration(
        """
        jib {
          from {
            image = "%s"
            platforms {
              platform {
                architecture = "amd64"
                os = "linux"
              }
            }
          }
          to {
            image = "%s:latest"
          }
          container {
            entrypoint = listOf("bash", "-c", "/entrypoint.sh")
            ports = listOf("%s")
            environment = mapOf(
             "SPRING_OUTPUT_ANSI_ENABLED" to "ALWAYS",
             "SEED4J_SLEEP" to "0"
            )
            creationTime = "USE_CURRENT_TIMESTAMP"
            user = "1000"
          }
          extraDirectories {
            paths {
              path {
                setFrom("src/main/docker/jib")
              }
            }
            permissions = mapOf("/entrypoint.sh" to "755")
          }
        }""".formatted(dockerBaseImage(properties), properties.projectBaseName().get(), properties.serverPort().get())
      )
      .build();
  }

  public Seed4JModule buildDockerFileMavenModule(Seed4JModuleProperties properties) {
    Assert.notNull(PROPERTIES_FIELD, properties);

    return moduleBuilder(properties).files().add(SOURCE.template("Dockerfile-maven"), to("Dockerfile")).and().build();
  }

  public Seed4JModule buildDockerFileGradleModule(Seed4JModuleProperties properties) {
    Assert.notNull(PROPERTIES_FIELD, properties);

    return moduleBuilder(properties).files().add(SOURCE.template("Dockerfile-gradle"), to("Dockerfile")).and().build();
  }

  public Seed4JModule buildSpringBootDockerComposeModule(Seed4JModuleProperties properties) {
    Assert.notNull(PROPERTIES_FIELD, properties);

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(springBootDockerComposeIntegration())
        .and()
      .springTestProperties()
        .set(propertyKey("spring.docker.compose.enabled"), propertyValue(false))
        .and()
      .build();
    // @formatter:on
  }

  private JavaDependency springBootDockerComposeIntegration() {
    return JavaDependency.builder()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-docker-compose")
      .scope(RUNTIME)
      .optional()
      .build();
  }
}
