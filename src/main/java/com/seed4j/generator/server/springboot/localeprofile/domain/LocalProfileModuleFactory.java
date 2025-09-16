package com.seed4j.generator.server.springboot.localeprofile.domain;

import static com.seed4j.module.domain.Seed4JModule.buildProfileActivation;
import static com.seed4j.module.domain.Seed4JModule.buildPropertyKey;
import static com.seed4j.module.domain.Seed4JModule.buildPropertyValue;
import static com.seed4j.module.domain.Seed4JModule.mavenPlugin;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.path;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.text;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.buildproperties.PropertyKey;
import com.seed4j.module.domain.buildproperties.PropertyValue;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class LocalProfileModuleFactory {

  private static final String DELIMITER = "@";
  private static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .javaBuildProperties()
        .set(new PropertyKey(SPRING_PROFILES_ACTIVE), new PropertyValue(""))
        .and()
      .gradleConfigurations()
        .addConfiguration(
          """
          tasks.build {
            dependsOn("processResources")
          }

          tasks.processResources {
            filesMatching("**/*.yml") {
              filter { it.replace("@spring.profiles.active@", springProfilesActive) }
            }
            filesMatching("**/*.properties") {
              filter { it.replace("@spring.profiles.active@", springProfilesActive) }
            }
          }
          """
        )
        .and()
      .javaBuildProfiles()
        .addProfile("local")
          .activation(buildProfileActivation().activeByDefault())
          .properties()
            .set(springActiveProfileProperty(), buildPropertyValue("local"))
            .and()
          .and()
        .and()
      .mavenPlugins()
        .pluginManagement(resourcesPlugin())
        .and()
      .springMainProperties()
        .set(propertyKey(SPRING_PROFILES_ACTIVE), propertyValue(DELIMITER + springActiveProfileProperty().get() + DELIMITER))
        .and()
      .optionalReplacements()
        .in(path(".github/workflows/github-actions.yml"))
          .add(text("./mvnw clean verify"), "./mvnw clean verify -P'!local'")
          .add(text("./gradlew clean integrationTest --no-daemon"), "./gradlew clean integrationTest -Pprofile=local --no-daemon")
          .and()
        .in(path(".gitlab-ci.yml"))
          .add(text("./mvnw clean verify"), "./mvnw clean verify -P'!local'")
      .add(text("./gradlew clean integrationTest $GRADLE_CLI_OPTS"), "./gradlew clean integrationTest -Pprofile=local $GRADLE_CLI_OPTS")
          .and()
        .and()
      .build();
    // @formatter:on
  }

  private static PropertyKey springActiveProfileProperty() {
    return buildPropertyKey(SPRING_PROFILES_ACTIVE);
  }

  private MavenPlugin resourcesPlugin() {
    return mavenPlugin()
      .groupId("org.apache.maven.plugins")
      .artifactId("maven-resources-plugin")
      .versionSlug("maven-resources-plugin")
      .configuration(
        """
        <useDefaultDelimiters>false</useDefaultDelimiters>
        <delimiters>
          <delimiter>%s</delimiter>
        </delimiters>
        """.formatted(DELIMITER)
      )
      .build();
  }
}
