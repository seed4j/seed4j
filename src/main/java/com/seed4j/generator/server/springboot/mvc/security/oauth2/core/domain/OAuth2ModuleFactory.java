package com.seed4j.generator.server.springboot.mvc.security.oauth2.core.domain;

import static com.seed4j.generator.server.springboot.mvc.security.common.domain.AuthenticationModuleFactory.authenticationModuleBuilder;
import static com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.dockerComposeFile;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.lineBeforeText;
import static com.seed4j.module.domain.Seed4JModule.path;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.text;
import static com.seed4j.module.domain.Seed4JModule.to;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.docker.DockerImageVersion;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javaproperties.PropertyValue;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.replacement.TextNeedleBeforeReplacer;
import com.seed4j.shared.error.domain.Assert;
import java.util.regex.Pattern;

public class OAuth2ModuleFactory {

  public static final String REALM_NAME = "keycloakRealmName";
  public static final String DEFAULT_REALM_NAME = "seed4j";
  public static final String CLIENT_SCOPE_NAME = "keycloakClientScopeName";
  public static final String DEFAULT_CLIENT_SCOPE_NAME = "seed4j";

  private static final Pattern NAME_FORMAT = Pattern.compile("^[a-z0-9-]+$");
  private static final TextNeedleBeforeReplacer IMPORT_NEEDLE = lineBeforeText(
    "import org.springframework.boot.test.context.SpringBootTest;"
  );
  private static final GroupId SPRING_GROUP = groupId("org.springframework.boot");

  private static final Seed4JSource SOURCE = from("server/springboot/mvc/security/oauth2/core");
  private static final Seed4JSource MAIN_SOURCE = SOURCE.append("main");
  private static final Seed4JSource TEST_SOURCE = SOURCE.append("test");
  private static final Seed4JSource DOCKER_SOURCE = SOURCE.append("docker");
  private static final Seed4JDestination DOCKER_DESTINATION = to("src/main/docker");

  private static final String APPLICATION = "application";
  private static final String PRIMARY = "infrastructure/primary";
  private static final String AUTHENTICATION_DESTINATION = "shared/authentication";

  private static final PropertyValue CLIENT_ID = propertyValue("web_app");
  private static final PropertyValue CLIENT_SECRET = propertyValue("web_app");

  private final DockerImages dockerImages;

  public OAuth2ModuleFactory(DockerImages dockerImages) {
    Assert.notNull("dockerImages", dockerImages);

    this.dockerImages = dockerImages;
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    var realmName = properties.getOrDefaultString(REALM_NAME, DEFAULT_REALM_NAME);
    var clientScopeName = properties.getOrDefaultString(CLIENT_SCOPE_NAME, DEFAULT_CLIENT_SCOPE_NAME);
    Assert.field(REALM_NAME, realmName).notNull().matchesPattern(NAME_FORMAT).maxLength(30);
    Assert.field(CLIENT_SCOPE_NAME, clientScopeName).notNull().matchesPattern(NAME_FORMAT).maxLength(30);

    Seed4JModuleBuilder builder = authenticationModuleBuilder(properties);

    appendKeycloak(builder, realmName, clientScopeName);
    appendJavaFiles(builder, properties);
    appendDependencies(builder);
    appendSpringProperties(builder, realmName);
    appendIntegrationTestAnnotationUpdates(builder, properties);

    return builder.build();
  }

  private void appendKeycloak(Seed4JModuleBuilder builder, String realmName, String clientScopeName) {
    DockerImageVersion keycloakImage = dockerImages.get("quay.io/keycloak/keycloak");

    builder
      .context()
      .put("dockerKeycloakVersion", keycloakImage.version().get())
      .put("dockerKeycloakImage", keycloakImage.fullName())
      .put("realmName", realmName)
      .put("clientScopeName", clientScopeName);

    builder
      .files()
      .add(DOCKER_SOURCE.template("keycloak.yml"), DOCKER_DESTINATION.append("keycloak.yml"))
      .add(
        DOCKER_SOURCE.template("seed4j-realm.json"),
        DOCKER_DESTINATION.append("keycloak-realm-config").append(realmName + "-realm.json")
      );

    builder.dockerComposeFile().append(dockerComposeFile("src/main/docker/keycloak.yml"));
  }

  private static void appendJavaFiles(Seed4JModuleBuilder builder, Seed4JModuleProperties properties) {
    String packagePath = properties.basePackage().path();
    Seed4JDestination mainDestination = toSrcMainJava().append(packagePath).append(AUTHENTICATION_DESTINATION);
    Seed4JDestination testDestination = toSrcTestJava().append(packagePath).append(AUTHENTICATION_DESTINATION);

    // @formatter:off
    builder
    .startupCommands()
      .dockerCompose("src/main/docker/keycloak.yml")
      .and()
    .files()
      .add(MAIN_SOURCE.append(APPLICATION).template("AuthenticatedUser.java"), mainDestination.append(APPLICATION).append("AuthenticatedUser.java"))
      .batch(MAIN_SOURCE.append(PRIMARY), mainDestination.append(PRIMARY))
        .addTemplate("ApplicationSecurityProperties.java")
        .addTemplate("AudienceValidator.java")
        .addTemplate("Claims.java")
        .addTemplate("CustomClaimConverter.java")
        .addTemplate("JwtGrantedAuthorityConverter.java")
        .addTemplate("OAuth2Configuration.java")
        .addTemplate("SecurityConfiguration.java")
        .and()
      .add(TEST_SOURCE.append(APPLICATION).template("AuthenticatedUserTest.java"), testDestination.append(APPLICATION).append("AuthenticatedUserTest.java"))
      .batch(TEST_SOURCE.append(PRIMARY), testDestination.append(PRIMARY))
        .addTemplate("ApplicationSecurityPropertiesTest.java")
        .addTemplate("AudienceValidatorTest.java")
        .addTemplate("ClaimsTest.java")
        .addTemplate("CustomClaimConverterTest.java")
        .addTemplate("FakeRequestAttributes.java")
        .addTemplate("JwtGrantedAuthorityConverterTest.java")
        .addTemplate("SecurityConfigurationIT.java")
        .addTemplate("TestSecurityConfiguration.java")
        .addTemplate("WithUnauthenticatedMockUser.java");
    // @formatter:on
  }

  private static void appendDependencies(Seed4JModuleBuilder builder) {
    builder
      .javaDependencies()
      .addDependency(SPRING_GROUP, artifactId("spring-boot-starter-oauth2-client"))
      .addDependency(SPRING_GROUP, artifactId("spring-boot-starter-oauth2-resource-server"));
  }

  private static void appendSpringProperties(Seed4JModuleBuilder builder, String realmName) {
    builder
      .springMainProperties()
      .set(
        propertyKey("spring.security.oauth2.client.provider.oidc.issuer-uri"),
        propertyValue("http://localhost:9080/realms/" + realmName)
      )
      .set(propertyKey("spring.security.oauth2.client.registration.oidc.client-id"), CLIENT_ID)
      .set(propertyKey("spring.security.oauth2.client.registration.oidc.client-secret"), CLIENT_SECRET)
      .set(propertyKey("spring.security.oauth2.client.registration.oidc.scope"), propertyValue("openid,profile,email"))
      .set(propertyKey("application.security.oauth2.audience"), propertyValue("account,api://default"));

    builder
      .springTestProperties()
      .set(propertyKey("spring.main.allow-bean-definition-overriding"), propertyValue(true))
      .set(
        propertyKey("spring.security.oauth2.client.provider.oidc.issuer-uri"),
        propertyValue("http://DO_NOT_CALL:9080/realms/" + realmName)
      );
  }

  private static void appendIntegrationTestAnnotationUpdates(Seed4JModuleBuilder builder, Seed4JModuleProperties properties) {
    String baseClass = properties.projectBaseName().capitalized() + "App.class";

    builder
      .mandatoryReplacements()
      .in(path("src/test/java").append(properties.packagePath()).append("IntegrationTest.java"))
      .add(IMPORT_NEEDLE, testSecurityConfigurationImport(properties))
      .add(text(baseClass), baseClass + ", TestSecurityConfiguration.class");
  }

  private static String testSecurityConfigurationImport(Seed4JModuleProperties properties) {
    return "import %s.shared.authentication.infrastructure.primary.TestSecurityConfiguration;".formatted(properties.basePackage().get());
  }
}
