package com.seed4j.generator.server.springboot.mvc.security.oauth2.okta.domain;

import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.propertyKey;
import static com.seed4j.module.domain.JHipsterModule.propertyValue;
import static com.seed4j.module.domain.JHipsterModule.to;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.javaproperties.PropertyValue;
import com.seed4j.module.domain.javaproperties.SpringProfile;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class OAuth2OktaModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/mvc/security/oauth2/okta");

  private static final SpringProfile OKTA_SPRING_PROFILE = new SpringProfile("okta");

  private static final String OKTA_CLIENT_ID_PROPERTY = "oktaClientId";
  private static final String OKTA_DOMAIN_PROPERTY = "oktaDomain";
  private static final String OKTA_SHELL_SCRIPT = "okta.sh";

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Okta"), SOURCE.template("documentation/okta.md"))
      .gitIgnore()
        .comment("OAuth 2.0")
        .pattern(OKTA_SHELL_SCRIPT)
        .and()
      .files()
        .add(SOURCE.file(OKTA_SHELL_SCRIPT), to(OKTA_SHELL_SCRIPT))
        .add(SOURCE.file("documentation/images/security-add-claim.png"), to("documentation/images/security-add-claim.png"))
        .and()
      .springMainProperties(OKTA_SPRING_PROFILE)
        .set(propertyKey("spring.security.oauth2.client.provider.oidc.issuer-uri"), issuerUri(properties))
        .set(propertyKey("spring.security.oauth2.client.registration.oidc.client-id"), clientId(properties))
        .and()
      .build();
    // @formatter:on
  }

  private static PropertyValue issuerUri(SeedModuleProperties properties) {
    return propertyValue("https://" + properties.getString(OKTA_DOMAIN_PROPERTY) + "/oauth2/default");
  }

  private static PropertyValue clientId(SeedModuleProperties properties) {
    return propertyValue(properties.getString(OKTA_CLIENT_ID_PROPERTY));
  }
}
