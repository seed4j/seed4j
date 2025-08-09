package com.seed4j.generator.server.springboot.localeprofile.domain;

import static com.seed4j.module.domain.SeedModule.buildProfileActivation;
import static com.seed4j.module.domain.SeedModule.buildPropertyKey;
import static com.seed4j.module.domain.SeedModule.buildPropertyValue;
import static com.seed4j.module.domain.SeedModule.mavenPlugin;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.path;
import static com.seed4j.module.domain.SeedModule.propertyKey;
import static com.seed4j.module.domain.SeedModule.propertyValue;
import static com.seed4j.module.domain.SeedModule.text;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.buildproperties.PropertyKey;
import com.seed4j.module.domain.buildproperties.PropertyValue;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class LocalProfileModuleFactory {

  private static final String DELIMITER = "@";
  private static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

  public SeedModule buildModule(SeedModuleProperties properties) {
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
