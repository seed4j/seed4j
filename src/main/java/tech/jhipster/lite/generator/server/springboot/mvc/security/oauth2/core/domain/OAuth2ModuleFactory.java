package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.core.domain;

import static tech.jhipster.lite.generator.server.springboot.mvc.security.common.domain.AuthenticationModulesFactory.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImageVersion;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javaproperties.PropertyValue;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.replacement.TextNeedleBeforeReplacer;
import tech.jhipster.lite.shared.error.domain.Assert;

public class OAuth2ModuleFactory {

  private static final TextNeedleBeforeReplacer IMPORT_NEEDLE = lineBeforeText(
    "import org.springframework.boot.test.context.SpringBootTest;"
  );
  private static final GroupId SPRING_GROUP = groupId("org.springframework.boot");

  private static final JHipsterSource SOURCE = from("server/springboot/mvc/security/oauth2/core");
  private static final JHipsterSource MAIN_SOURCE = SOURCE.append("main");
  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");
  private static final JHipsterSource DOCKER_SOURCE = SOURCE.append("docker");
  private static final JHipsterDestination DOCKER_DESTINATION = to("src/main/docker");

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

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    JHipsterModuleBuilder builder = authenticationModuleBuilder(properties);

    appendKeycloak(builder);
    appendJavaFiles(builder, properties);
    appendDependencies(builder);
    appendSpringProperties(builder);
    appendIntegrationTestAnnotationUpdates(builder, properties);

    return builder.build();
  }

  private void appendKeycloak(JHipsterModuleBuilder builder) {
    DockerImageVersion keycloakImage = dockerImages.get("quay.io/keycloak/keycloak");

    builder.context().put("dockerKeycloakVersion", keycloakImage.version().get()).put("dockerKeycloakImage", keycloakImage.fullName());

    builder
      .files()
      .add(DOCKER_SOURCE.template("keycloak.yml"), DOCKER_DESTINATION.append("keycloak.yml"))
      .batch(DOCKER_SOURCE, DOCKER_DESTINATION.append("keycloak-realm-config"))
      .addTemplate("jhipster-realm.json");
  }

  private void appendJavaFiles(JHipsterModuleBuilder builder, JHipsterModuleProperties properties) {
    String packagePath = properties.basePackage().path();
    JHipsterDestination mainDestination = toSrcMainJava().append(packagePath).append(AUTHENTICATION_DESTINATION);
    JHipsterDestination testDestination = toSrcTestJava().append(packagePath).append(AUTHENTICATION_DESTINATION);

    //@formatter:off
    builder
    .startupCommand("docker compose -f src/main/docker/keycloak.yml up -d")
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
    //@formatter:on
  }

  private void appendDependencies(JHipsterModuleBuilder builder) {
    builder
      .javaDependencies()
      .addDependency(SPRING_GROUP, artifactId("spring-boot-starter-oauth2-client"))
      .addDependency(SPRING_GROUP, artifactId("spring-boot-starter-oauth2-resource-server"));
  }

  private void appendSpringProperties(JHipsterModuleBuilder builder) {
    builder
      .springMainProperties()
      .set(propertyKey("spring.security.oauth2.client.provider.oidc.issuer-uri"), propertyValue("http://localhost:9080/realms/jhipster"))
      .set(propertyKey("spring.security.oauth2.client.registration.oidc.client-id"), CLIENT_ID)
      .set(propertyKey("spring.security.oauth2.client.registration.oidc.client-secret"), CLIENT_SECRET)
      .set(propertyKey("spring.security.oauth2.client.registration.oidc.scope"), propertyValue("openid,profile,email"))
      .set(propertyKey("application.security.oauth2.audience"), propertyValue("account,api://default"));

    builder
      .springTestProperties()
      .set(propertyKey("spring.main.allow-bean-definition-overriding"), propertyValue("true"))
      .set(propertyKey("spring.security.oauth2.client.registration.oidc.client-id"), CLIENT_ID)
      .set(propertyKey("spring.security.oauth2.client.provider.oidc.issuer-uri"), propertyValue("http://DO_NOT_CALL:9080/realms/jhipster"));
  }

  private void appendIntegrationTestAnnotationUpdates(JHipsterModuleBuilder builder, JHipsterModuleProperties properties) {
    String baseClass = properties.projectBaseName().capitalized() + "App.class";

    builder
      .mandatoryReplacements()
      .in(path("src/test/java").append(properties.packagePath()).append("IntegrationTest.java"))
      .add(IMPORT_NEEDLE, testSecurityConfigurationImport(properties))
      .add(text(baseClass), baseClass + ", TestSecurityConfiguration.class");
  }

  private String testSecurityConfigurationImport(JHipsterModuleProperties properties) {
    return new StringBuilder()
      .append("import ")
      .append(properties.basePackage().get())
      .append(".shared.authentication.infrastructure.primary.TestSecurityConfiguration;")
      .toString();
  }
}
