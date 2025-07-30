package com.seed4j.generator.server.springboot.mvc.security.jwt.domain;

import static com.seed4j.generator.server.springboot.mvc.security.common.domain.AuthenticationModuleFactory.authenticationModuleBuilder;
import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.javaDependency;
import static com.seed4j.module.domain.JHipsterModule.propertyKey;
import static com.seed4j.module.domain.JHipsterModule.propertyValue;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;
import static com.seed4j.module.domain.JHipsterModule.versionSlug;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.file.JHipsterDestination;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.javaproperties.PropertyKey;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.base64.domain.Base64Utils;
import com.seed4j.shared.error.domain.Assert;

public class JwtAuthenticationModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/mvc/security/jwt/authentication");
  private static final JHipsterSource MAIN_SOURCE = SOURCE.append("main");
  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");

  private static final GroupId JJWT_GROUP = groupId("io.jsonwebtoken");
  private static final VersionSlug JJWT_VERSION = versionSlug("json-web-token");

  private static final String APPLICATION = "application";
  private static final String PRIMARY = "infrastructure/primary";
  private static final String AUTHENTICATION_DESTINATION = "shared/authentication";

  private static final PropertyKey BASE_SECRET_64_PROPERTY_KEY = propertyKey("application.security.jwt-base64-secret");
  private static final String JWT_BASE_64_SECRET = "jwtBase64Secret";

  private static final String SPRING_SECURITY_PACKAGE = "org.springframework.security";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    String mainJwtBase64secret = properties.getOrDefaultString(JWT_BASE_64_SECRET, Base64Utils.getBase64Secret());
    String testJwtBase64secret = properties.getOrDefaultString(JWT_BASE_64_SECRET, Base64Utils.getBase64Secret());

    JHipsterDestination mainDestination = toSrcMainJava().append(packagePath).append(AUTHENTICATION_DESTINATION);
    JHipsterDestination testDestination = toSrcTestJava().append(packagePath).append(AUTHENTICATION_DESTINATION);

    // @formatter:off
    return authenticationModuleBuilder(properties)
      .javaDependencies()
        .addDependency(JJWT_GROUP, artifactId("jjwt-api"), JJWT_VERSION)
        .addDependency(jjwtImplDependency())
        .addDependency(jjwtJacksonDependency())
        .and()
      .files()
        .add(MAIN_SOURCE.append(APPLICATION).template("AuthenticatedUser.java"), mainDestination.append(APPLICATION).append("AuthenticatedUser.java"))
        .batch(MAIN_SOURCE.append(PRIMARY), mainDestination.append(PRIMARY))
          .addTemplate("AuthenticationTokenReader.java")
          .addTemplate("JwtAuthenticationProperties.java")
          .addTemplate("JWTConfigurer.java")
          .addTemplate("JWTFilter.java")
          .addTemplate("JwtReader.java")
          .addTemplate("SecurityConfiguration.java")
          .and()
        .add(TEST_SOURCE.append(APPLICATION).template("AuthenticatedUserTest.java"), testDestination.append(APPLICATION).append("AuthenticatedUserTest.java"))
        .batch(TEST_SOURCE.append(PRIMARY), testDestination.append(PRIMARY))
          .addTemplate("JWTFilterTest.java")
          .addTemplate("JwtReaderTest.java")
          .and()
        .and()
      .springMainProperties()
        .set(BASE_SECRET_64_PROPERTY_KEY, propertyValue(mainJwtBase64secret))
        .and()
      .springTestProperties()
        .set(BASE_SECRET_64_PROPERTY_KEY, propertyValue(testJwtBase64secret))
        .and()
      .springMainLogger(SPRING_SECURITY_PACKAGE, LogLevel.WARN)
      .springTestLogger(SPRING_SECURITY_PACKAGE, LogLevel.WARN)
      .build();
    // @formatter:on
  }

  private JavaDependency jjwtImplDependency() {
    return javaDependency()
      .groupId(JJWT_GROUP)
      .artifactId("jjwt-impl")
      .versionSlug(JJWT_VERSION)
      .scope(JavaDependencyScope.RUNTIME)
      .build();
  }

  private JavaDependency jjwtJacksonDependency() {
    return javaDependency()
      .groupId(JJWT_GROUP)
      .artifactId("jjwt-jackson")
      .versionSlug(JJWT_VERSION)
      .scope(JavaDependencyScope.RUNTIME)
      .build();
  }
}
