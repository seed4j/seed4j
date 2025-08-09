package com.seed4j.generator.server.springboot.mvc.security.jwt.domain;

import static com.seed4j.module.domain.SeedModule.SeedModuleBuilder;
import static com.seed4j.module.domain.SeedModule.documentationTitle;
import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.propertyKey;
import static com.seed4j.module.domain.SeedModule.propertyValue;
import static com.seed4j.module.domain.SeedModule.toSrcMainJava;
import static com.seed4j.module.domain.SeedModule.toSrcTestJava;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.javaproperties.SeedModuleSpringProperties.SeedModuleSpringPropertiesBuilder;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class JwtBasicAuthModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/mvc/security/jwt/basic-auth");
  private static final SeedSource MAIN_SOURCE = SOURCE.append("main");
  private static final SeedSource TEST_SOURCE = SOURCE.append("test");

  private static final String DOMAIN = "domain";
  private static final String ACCOUNT = "account";
  private static final String PRIMARY = "infrastructure/primary";
  private static final String SECONDARY = "infrastructure/secondary";

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    SeedDestination mainDestination = toSrcMainJava().append(packagePath).append(ACCOUNT);
    SeedDestination testDestination = toSrcTestJava().append(packagePath).append(ACCOUNT);

    // @formatter:off
    SeedModuleBuilder builder = moduleBuilder(properties)
      .documentation(documentationTitle("JWT basic auth"), SOURCE.template("jwt-basic-auth.md"))
      .files()
        .add(MAIN_SOURCE.template("package-info.java"), mainDestination.append("package-info.java"))
        .add(
          MAIN_SOURCE.template("application/AccountApplicationService.java"),
          mainDestination.append("application/AccountApplicationService.java")
        )
        .batch(MAIN_SOURCE.append(DOMAIN), mainDestination.append(DOMAIN))
          .addTemplate("AuthenticationQuery.java")
          .addTemplate("Token.java")
          .addTemplate("TokensRepository.java")
          .and()
        .batch(MAIN_SOURCE.append(PRIMARY), mainDestination.append(PRIMARY))
          .addTemplate("AccountResource.java")
          .addTemplate("AuthenticationResource.java")
          .addTemplate("Authenticator.java")
          .addTemplate("RestAccount.java")
          .addTemplate("RestAuthenticationQuery.java")
          .addTemplate("RestToken.java")
          .and()
        .batch(MAIN_SOURCE.append(SECONDARY), mainDestination.append(SECONDARY))
          .addTemplate("JwtTokensConfiguration.java")
          .addTemplate("JwtTokensProperties.java")
          .addTemplate("JwtTokensRepository.java")
          .and()
        .add(TEST_SOURCE.append(DOMAIN).template("TokensFixture.java"), testDestination.append(DOMAIN).append("TokensFixture.java"))
        .batch(TEST_SOURCE.append(PRIMARY), testDestination.append(PRIMARY))
          .addTemplate("AccountResourceIT.java")
          .addTemplate("AuthenticationResourceIT.java")
          .addTemplate("RestAccountTest.java")
          .addTemplate("RestAuthenticationQueryTest.java")
          .addTemplate("RestTokenTest.java")
          .and()
        .and();
    // @formatter:on

    appendProperties(builder.springMainProperties());

    return builder.build();
  }

  private void appendProperties(SeedModuleSpringPropertiesBuilder builder) {
    builder
      .set(propertyKey("application.security.token-validity"), propertyValue("P1D"))
      .set(propertyKey("application.security.remember-me-token-validity"), propertyValue("P365D"))
      .set(propertyKey("spring.security.user.name"), propertyValue("admin"))
      .set(propertyKey("spring.security.user.password"), propertyValue("$2a$12$cRKS9ZURbdJIaRsKDTDUmOrH4.B.2rokv8rrkrQXr2IR2Hkna484O"))
      .set(propertyKey("spring.security.user.roles"), propertyValue("ADMIN"))
      .set(
        propertyKey("application.security.content-security-policy"),
        propertyValue(
          """
          default-src 'self'; frame-src 'self' data:; \
          script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; \
          style-src 'self' 'unsafe-inline' https://fonts.googleapis.com; \
          img-src 'self' data:; \
          font-src 'self' data: https://fonts.gstatic.com;\
          """
        )
      );
  }
}
