package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.tools.domain.Constants.APPLICATION;
import static tech.jhipster.lite.generator.tools.domain.Constants.DOMAIN;
import static tech.jhipster.lite.generator.tools.domain.Constants.INFRASTRUCTURE;
import static tech.jhipster.lite.generator.tools.domain.Constants.INFRASTRUCTURE_CONFIG;
import static tech.jhipster.lite.generator.tools.domain.Constants.INFRASTRUCTURE_PRIMARY;
import static tech.jhipster.lite.generator.tools.domain.Constants.TECHNICAL_INFRASTRUCTURE_PRIMARY;

import java.util.HashMap;
import java.util.Map;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;

public class OAuth2Security {

  public static final String SECURITY_OAUTH2 = "security/oauth2";
  public static final String SECURITY_OAUTH2_APPLICATION = getPath(SECURITY_OAUTH2, APPLICATION);
  public static final String SECURITY_OAUTH2_DOMAIN = getPath(SECURITY_OAUTH2, DOMAIN);
  public static final String SECURITY_OAUTH2_INFRASTRUCTURE = getPath(SECURITY_OAUTH2, INFRASTRUCTURE);
  public static final String SECURITY_OAUTH2_INFRASTRUCTURE_CONFIG = getPath(SECURITY_OAUTH2, INFRASTRUCTURE_CONFIG);
  public static final String TECHNICAL_INFRASTRUCTURE_PRIMARY_EXCEPTION = getPath(TECHNICAL_INFRASTRUCTURE_PRIMARY, "exception");

  public static final String ERROR_DOMAIN = "error/domain";

  public static final String ACCOUNT_CONTEXT = "account";
  public static final String ACCOUNT_DOMAIN = getPath(ACCOUNT_CONTEXT, DOMAIN);
  public static final String ACCOUNT_INFRASTRUCTURE_PRIMARY = getPath(ACCOUNT_CONTEXT, INFRASTRUCTURE_PRIMARY, "rest");

  private static final String DOCKER_KEYCLOAK_IMAGE_NAME = "jboss/keycloak";

  private static final String SPRINGBOOT_PACKAGE = "org.springframework.boot";
  private static final String STARTER_SECURITY = "spring-boot-starter-security";
  private static final String STARTER_OAUTH2_CLIENT = "spring-boot-starter-oauth2-client";
  private static final String STARTER_OAUTH2_RESOURCE_SERVER = "spring-boot-starter-oauth2-resource-server";

  private OAuth2Security() {}

  public static String getDockerKeycloakImageName() {
    return DOCKER_KEYCLOAK_IMAGE_NAME;
  }

  public static Dependency springBootStarterSecurityDependency() {
    return Dependency.builder().groupId(SPRINGBOOT_PACKAGE).artifactId(STARTER_SECURITY).build();
  }

  public static Dependency springBootStarterOAuth2ClientDependency() {
    return Dependency.builder().groupId(SPRINGBOOT_PACKAGE).artifactId(STARTER_OAUTH2_CLIENT).build();
  }

  public static Dependency springBootStarterOAuth2ResourceServerDependency() {
    return Dependency.builder().groupId(SPRINGBOOT_PACKAGE).artifactId(STARTER_OAUTH2_RESOURCE_SERVER).build();
  }

  public static Dependency springSecurityTestDependency() {
    return Dependency.builder().groupId("org.springframework.security").artifactId("spring-security-test").scope("test").build();
  }

  public static Map<String, String> oauth2SecurityFiles() {
    Map<String, String> map = new HashMap<>();

    map.put("SecurityUtils.java", SECURITY_OAUTH2_APPLICATION);

    map.put("AuthoritiesConstants.java", SECURITY_OAUTH2_DOMAIN);
    map.put("ApplicationSecurityDefaults.java", SECURITY_OAUTH2_DOMAIN);

    map.put("ApplicationSecurityProperties.java", SECURITY_OAUTH2_INFRASTRUCTURE_CONFIG);
    map.put("AudienceValidator.java", SECURITY_OAUTH2_INFRASTRUCTURE_CONFIG);
    map.put("CustomClaimConverter.java", SECURITY_OAUTH2_INFRASTRUCTURE_CONFIG);
    map.put("JwtGrantedAuthorityConverter.java", SECURITY_OAUTH2_INFRASTRUCTURE_CONFIG);
    map.put("OAuth2Configuration.java", SECURITY_OAUTH2_INFRASTRUCTURE_CONFIG);
    map.put("SecurityConfiguration.java", SECURITY_OAUTH2_INFRASTRUCTURE_CONFIG);

    map.put("AccountException.java", ERROR_DOMAIN);

    return map;
  }

  public static Map<String, String> oauth2TestSecurityFiles() {
    Map<String, String> map = new HashMap<>();

    map.put("SecurityUtilsTest.java", SECURITY_OAUTH2_APPLICATION);

    map.put("ApplicationSecurityPropertiesTest.java", SECURITY_OAUTH2_INFRASTRUCTURE_CONFIG);
    map.put("AudienceValidatorTest.java", SECURITY_OAUTH2_INFRASTRUCTURE_CONFIG);
    map.put("CustomClaimConverterIT.java", SECURITY_OAUTH2_INFRASTRUCTURE_CONFIG);
    map.put("FakeRequestAttributes.java", SECURITY_OAUTH2_INFRASTRUCTURE_CONFIG);
    map.put("JwtGrantedAuthorityConverterTest.java", SECURITY_OAUTH2_INFRASTRUCTURE_CONFIG);
    map.put("SecurityConfigurationIT.java", SECURITY_OAUTH2_INFRASTRUCTURE_CONFIG);
    map.put("SecurityConfigurationTest.java", SECURITY_OAUTH2_INFRASTRUCTURE_CONFIG);
    map.put("TestSecurityConfiguration.java", SECURITY_OAUTH2_INFRASTRUCTURE_CONFIG);

    map.put("WithUnauthenticatedMockUser.java", SECURITY_OAUTH2_INFRASTRUCTURE);
    map.put("AccountExceptionTest.java", ERROR_DOMAIN);

    return map;
  }

  public static Map<String, String> properties() {
    return Map.of(
      "spring.security.oauth2.client.provider.oidc.issuer-uri",
      "http://localhost:9080/auth/realms/jhipster",
      "spring.security.oauth2.client.registration.oidc.client-id",
      "web_app",
      "spring.security.oauth2.client.registration.oidc.client-secret",
      "web_app",
      "spring.security.oauth2.client.registration.oidc.scope",
      "openid,profile,email",
      "application.security.oauth2.audience",
      "account,api://default"
    );
  }

  public static Map<String, String> propertiesForTests() {
    return Map.of(
      "spring.main.allow-bean-definition-overriding",
      "true",
      "spring.security.oauth2.client.provider.oidc.issuer-uri",
      "http://DO_NOT_CALL:9080/auth/realms/jhipster"
    );
  }

  public static Map<String, String> oauth2AccountContextFiles() {
    Map<String, String> map = new HashMap<>();

    map.put("AccountConstants.java", ACCOUNT_DOMAIN);
    map.put("AccountResource.java", ACCOUNT_INFRASTRUCTURE_PRIMARY);
    map.put("UserDTO.java", ACCOUNT_INFRASTRUCTURE_PRIMARY);

    return map;
  }

  public static Map<String, String> oauth2AccountContextTestFiles() {
    Map<String, String> map = new HashMap<>();

    map.put("AccountResourceIT.java", ACCOUNT_INFRASTRUCTURE_PRIMARY);
    map.put("AccountResourceTest.java", ACCOUNT_INFRASTRUCTURE_PRIMARY);
    map.put("OAuth2TestUtil.java", ACCOUNT_INFRASTRUCTURE_PRIMARY);
    map.put("UserDTOTest.java", ACCOUNT_INFRASTRUCTURE_PRIMARY);

    return map;
  }
}
