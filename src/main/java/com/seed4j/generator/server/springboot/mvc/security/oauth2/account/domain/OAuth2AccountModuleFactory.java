package com.seed4j.generator.server.springboot.mvc.security.oauth2.account.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;

public class OAuth2AccountModuleFactory {

  private static final String PACKAGE_INFO = "package-info.java";
  private static final String APPLICATION = "application";
  private static final String DOMAIN = "domain";
  private static final String INFRASTRUCTURE = "infrastructure";
  private static final String PRIMARY = INFRASTRUCTURE + "/primary";
  private static final String SECONDARY = INFRASTRUCTURE + "/secondary";
  private static final String USER_IDENTITY_DESTINATION = "shared/useridentity";

  private static final Seed4JSource ACCOUNT_SOURCE = from("server/springboot/mvc/security/oauth2/account");
  private static final Seed4JSource ACCOUNT_MAIN_SOURCE = ACCOUNT_SOURCE.append("main");
  private static final Seed4JSource ACCOUNT_TEST_SOURCE = ACCOUNT_SOURCE.append("test");

  private static final Seed4JSource USER_IDENTITY_SOURCE = from("server/springboot/mvc/security/oauth2/useridentity");
  private static final Seed4JSource USER_IDENTITY_MAIN_SOURCE = USER_IDENTITY_SOURCE.append("main");
  private static final Seed4JSource USER_IDENTITY_TEST_SOURCE = USER_IDENTITY_SOURCE.append("test");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    String packagePath = properties.packagePath();
    Seed4JDestination accountMainDestination = toSrcMainJava().append(packagePath).append("account");
    Seed4JDestination accountTestDestination = toSrcTestJava().append(packagePath).append("account");

    Seed4JDestination userIdentityMainDestination = toSrcMainJava().append(packagePath).append(USER_IDENTITY_DESTINATION);
    Seed4JDestination userIdentityTestDestination = toSrcTestJava().append(packagePath).append(USER_IDENTITY_DESTINATION);

    // @formatter:off
    return moduleBuilder(properties)
        .files()
          .add(ACCOUNT_MAIN_SOURCE.append(APPLICATION).template("AccountsApplicationService.java"), accountMainDestination.append(APPLICATION).append("AccountsApplicationService.java"))
          .batch(ACCOUNT_MAIN_SOURCE.append(DOMAIN), accountMainDestination.append(DOMAIN))
            .addTemplate("Account.java")
            .addTemplate("AccountsRepository.java")
            .and()
          .batch(ACCOUNT_MAIN_SOURCE.append(PRIMARY), accountMainDestination.append(PRIMARY))
            .addTemplate("RestAccount.java")
            .addTemplate("AccountsResource.java")
            .and()
          .batch(ACCOUNT_MAIN_SOURCE.append(SECONDARY), accountMainDestination.append(SECONDARY))
            .addTemplate("OAuth2AccountsRepository.java")
            .addTemplate("OAuth2AuthenticationReader.java")
            .addTemplate("UnknownAuthenticationSchemeException.java")
            .and()
          .add(ACCOUNT_MAIN_SOURCE.template(PACKAGE_INFO), accountMainDestination.append(PACKAGE_INFO))
          .add(ACCOUNT_TEST_SOURCE.append(DOMAIN).template("AccountsFixture.java"), accountTestDestination.append(DOMAIN).append("AccountsFixture.java"))
          .batch(ACCOUNT_TEST_SOURCE.append(PRIMARY), accountTestDestination.append(PRIMARY))
            .addTemplate("RestAccountTest.java")
            .addTemplate("AccountsResourceIT.java")
            .addTemplate("AccountsResourceTest.java")
            .and()
          .add(ACCOUNT_TEST_SOURCE.append(SECONDARY).template("OAuth2AuthenticationReaderTest.java"), accountTestDestination.append(SECONDARY).append("OAuth2AuthenticationReaderTest.java"))
          .add(ACCOUNT_TEST_SOURCE.append(INFRASTRUCTURE).template("OAuth2TokenFixture.java"), accountTestDestination.append(INFRASTRUCTURE).append("OAuth2TokenFixture.java"))
          .batch(USER_IDENTITY_MAIN_SOURCE.append(DOMAIN), userIdentityMainDestination.append(DOMAIN))
            .addTemplate("Email.java")
            .addTemplate("Firstname.java")
            .addTemplate("Lastname.java")
            .addTemplate("Name.java")
            .and()
          .add(USER_IDENTITY_MAIN_SOURCE.template(PACKAGE_INFO), userIdentityMainDestination.append(PACKAGE_INFO))
          .batch(USER_IDENTITY_TEST_SOURCE.append(DOMAIN), userIdentityTestDestination.append(DOMAIN))
            .addTemplate("EmailTest.java")
            .addTemplate("FirstnameTest.java")
            .addTemplate("LastnameTest.java")
            .addTemplate("NameTest.java")
            .addTemplate("UsersIdentitiesFixture.java")
            .and()
          .and()
        .build();
    // @formatter:on
  }
}
