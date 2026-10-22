package com.seed4j.generator.server.micronaut.security.jwt.domain;

import static com.seed4j.generator.server.micronaut.common.domain.MicronautProperties.MICRONAUT_MAIN;
import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javaproperties.Seed4JModuleApplicationProperties.Seed4JModuleApplicationPropertiesBuilder;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.base64.domain.Base64Utils;
import com.seed4j.shared.error.domain.Assert;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class MicronautSecurityJwtModuleFactory {

  private static final Seed4JSource SOURCE = from("server/micronaut/security/jwt");
  private static final GroupId MICRONAUT_SECURITY_GROUP = groupId("io.micronaut.security");

  private static final String ANONYMOUS = "isAnonymous()";
  private static final String AUTHENTICATED = "isAuthenticated()";
  private static final String ADMIN = "ROLE_ADMIN";

  // Ordered: the first matching pattern wins, everything that doesn't match is rejected
  private static final List<UrlAccess> URL_ACCESSES = List.of(
    new UrlAccess("/management/health", ANONYMOUS),
    new UrlAccess("/management/health/**", ANONYMOUS),
    new UrlAccess("/management/info", ANONYMOUS),
    new UrlAccess("/management/**", ADMIN),
    new UrlAccess("/swagger/**", ANONYMOUS),
    new UrlAccess("/swagger-ui/**", ANONYMOUS),
    new UrlAccess("/api/admin/**", ADMIN),
    new UrlAccess("/api/**", AUTHENTICATED)
  );

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String jwtBase64Secret = properties.getOrDefaultString("jwtBase64Secret", Base64Utils.getBase64Secret());
    Seed4JDestination testDestination = toSrcTestJava()
      .append(properties.packagePath())
      .append("shared/authentication/infrastructure/primary");

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(MICRONAUT_SECURITY_GROUP, artifactId("micronaut-security-jwt"))
        .and()
      .files()
        .batch(SOURCE.append("test"), testDestination)
          .addTemplate("SecurityRulesTest.java")
          .addTemplate("SecuredTestResource.java")
          .and()
          .nullMarkedJavaPackage(testDestination)
        .and()
      .applicationProperties(MICRONAUT_MAIN)
        .set(propertyKey("micronaut.security.authentication"), propertyValue("bearer"))
        .set(propertyKey("micronaut.security.token.jwt.signatures.secret.generator.secret"), propertyValue("${JWT_BASE64_SECRET:" + jwtBase64Secret + "}"))
        .set(propertyKey("micronaut.security.token.jwt.signatures.secret.generator.base64"), propertyValue(true))
        .set(propertyKey("micronaut.security.token.jwt.generator.access-token.expiration"), propertyValue(86400))
        .and()
      .with(urlAccesses())
      .build();
    // @formatter:on
  }

  private static Consumer<Seed4JModuleBuilder> urlAccesses() {
    return builder -> {
      Seed4JModuleApplicationPropertiesBuilder applicationProperties = builder.applicationProperties(MICRONAUT_MAIN);

      IntStream.range(0, URL_ACCESSES.size()).forEach(index -> URL_ACCESSES.get(index).appendTo(applicationProperties, index));
    };
  }

  private record UrlAccess(String pattern, String access) {
    void appendTo(Seed4JModuleApplicationPropertiesBuilder applicationProperties, int index) {
      String prefix = "micronaut.security.intercept-url-map[" + index + "].";

      applicationProperties
        .set(propertyKey(prefix + "pattern"), propertyValue(pattern))
        .set(propertyKey(prefix + "access"), propertyValue(access));
    }
  }
}
