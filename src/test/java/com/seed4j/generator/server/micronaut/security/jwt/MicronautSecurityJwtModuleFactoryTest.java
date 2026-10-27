package com.seed4j.generator.server.micronaut.security.jwt;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class MicronautSecurityJwtModuleFactoryTest {

  private final MicronautSecurityJwtModuleFactory factory = new MicronautSecurityJwtModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .put("jwtBase64Secret", "c2VjcmV0")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/shared/authentication/infrastructure/primary",
        "SecurityRulesTest.java",
        "SecuredTestResource.java"
      )
      .hasFile("pom.xml")
      .containing(
        """
              <groupId>io.micronaut.security</groupId>
              <artifactId>micronaut-security-jwt</artifactId>\
        """
      )
      .and()
      .hasFile("src/main/resources/application.yml")
      .containing("micronaut:")
      .containing("security:")
      .containing("authentication: bearer")
      .containing("secret: ${JWT_BASE64_SECRET:c2VjcmV0}")
      .containing("base64: true")
      .containing(
        """
            intercept-url-map[0]:
              access: isAnonymous()
              pattern: /management/health
        """
      )
      .containing(
        """
            intercept-url-map[7]:
              access: isAuthenticated()
              pattern: /api/**
        """
      );
  }
}
