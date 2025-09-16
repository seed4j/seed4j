package com.seed4j.generator.server.springboot.apidocumentation.springdocokta.domain;

import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.javaproperties.PropertyValue;
import com.seed4j.module.domain.javaproperties.SpringProfile;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SpringdocOktaModuleFactory {

  private static final SpringProfile OKTA_SPRING_PROFILE = new SpringProfile("okta");

  private static final String OKTA_CLIENT_ID_PROPERTY = "oktaClientId";
  private static final String OKTA_DOMAIN_PROPERTY = "oktaDomain";

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .springMainProperties(OKTA_SPRING_PROFILE)
        .set(propertyKey("springdoc.swagger-ui.oauth.client-id"), clientId(properties))
        .set(propertyKey("springdoc.swagger-ui.oauth.realm"), propertyValue("seed4j"))
        .set(propertyKey("springdoc.swagger-ui.oauth.scopes"), propertyValue("openid","profile","email"))
        .set(propertyKey("springdoc.oauth2.authorization-url"), authorizationUrl(properties))
        .and()
      .build();
    // @formatter:on
  }

  private static PropertyValue clientId(Seed4JModuleProperties properties) {
    return propertyValue(properties.getString(OKTA_CLIENT_ID_PROPERTY));
  }

  private static PropertyValue authorizationUrl(Seed4JModuleProperties properties) {
    return propertyValue(
      new StringBuilder()
        .append("https://")
        .append(properties.getString(OKTA_DOMAIN_PROPERTY))
        .append("/oauth2/default/v1/authorize?nonce=\"seed4j\"")
        .toString()
    );
  }
}
