package com.seed4j.generator.server.springboot.mvc.security.jwt.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class JwtBasicAuthModuleFactoryTest {

  private static final JwtBasicAuthModuleFactory factory = new JwtBasicAuthModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("seed4j")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFiles("documentation/jwt-basic-auth.md")
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        application:
          security:
            content-security-policy: 'default-src ''self''; frame-src ''self'' data:; script-src
              ''self'' ''unsafe-inline'' ''unsafe-eval'' https://storage.googleapis.com; style-src
              ''self'' ''unsafe-inline'' https://fonts.googleapis.com; img-src ''self'' data:;
              font-src ''self'' data: https://fonts.gstatic.com;'
            remember-me-token-validity: P365D
            token-validity: P1D
        spring:
          security:
            user:
              name: admin
              password: $2a$12$cRKS9ZURbdJIaRsKDTDUmOrH4.B.2rokv8rrkrQXr2IR2Hkna484O
              roles: ADMIN
        """
      )
      .and()
      .hasFiles("src/main/java/com/seed4j/growth/account/package-info.java")
      .hasFiles("src/main/java/com/seed4j/growth/account/application/AccountApplicationService.java")
      .hasPrefixedFiles("src/main/java/com/seed4j/growth/account/domain", "AuthenticationQuery.java", "Token.java", "TokensRepository.java")
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/account/infrastructure/primary",
        "AccountResource.java",
        "AuthenticationResource.java",
        "Authenticator.java",
        "RestAccount.java",
        "RestAuthenticationQuery.java",
        "RestToken.java"
      )
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/account/infrastructure/secondary",
        "JwtTokensConfiguration.java",
        "JwtTokensProperties.java",
        "JwtTokensRepository.java"
      )
      .hasFiles("src/test/java/com/seed4j/growth/account/domain/TokensFixture.java")
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/account/infrastructure/primary",
        "AccountResourceIT.java",
        "AuthenticationResourceIT.java",
        "RestAccountTest.java",
        "RestAuthenticationQueryTest.java",
        "RestTokenTest.java"
      );
  }
}
