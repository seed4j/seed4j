package com.seed4j.generator.server.springboot.mvc.security.oauth2.account.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class Oauth2AccountModuleFactoryTest {

  private static final OAuth2AccountModuleFactory factory = new OAuth2AccountModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasPrefixedFiles("src/main/java/tech/jhipster/jhlitest/account/domain", "Account.java", "AccountsRepository.java")
      .hasFile("src/main/java/tech/jhipster/jhlitest/account/application/AccountsApplicationService.java")
      .and()
      .hasPrefixedFiles("src/main/java/tech/jhipster/jhlitest/account/infrastructure/primary", "RestAccount.java", "AccountsResource.java")
      .hasPrefixedFiles(
        "src/main/java/tech/jhipster/jhlitest/account/infrastructure/secondary",
        "OAuth2AccountsRepository.java",
        "OAuth2AuthenticationReader.java",
        "UnknownAuthenticationSchemeException.java"
      )
      .hasFile("src/main/java/tech/jhipster/jhlitest/account/package-info.java")
      .and()
      .hasPrefixedFiles(
        "src/main/java/tech/jhipster/jhlitest/shared/useridentity/domain",
        "Email.java",
        "Firstname.java",
        "Lastname.java",
        "Name.java"
      )
      .hasFile("src/main/java/tech/jhipster/jhlitest/shared/useridentity/package-info.java")
      .and()
      .hasPrefixedFiles(
        "src/test/java/tech/jhipster/jhlitest/shared/useridentity/domain",
        "EmailTest.java",
        "FirstnameTest.java",
        "LastnameTest.java",
        "NameTest.java",
        "UsersIdentitiesFixture.java"
      )
      .hasFile("src/test/java/tech/jhipster/jhlitest/account/domain/AccountsFixture.java")
      .and()
      .hasPrefixedFiles(
        "src/test/java/tech/jhipster/jhlitest/account/infrastructure/primary",
        "RestAccountTest.java",
        "AccountsResourceIT.java",
        "AccountsResourceTest.java"
      )
      .hasFile("src/test/java/tech/jhipster/jhlitest/account/infrastructure/secondary/OAuth2AuthenticationReaderTest.java")
      .and()
      .hasFile("src/test/java/tech/jhipster/jhlitest/account/infrastructure/OAuth2TokenFixture.java");
  }
}
