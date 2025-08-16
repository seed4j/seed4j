package com.seed4j.generator.server.springboot.apidocumentation.springdocauth0.domain;

import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.propertyKey;
import static com.seed4j.module.domain.SeedModule.propertyValue;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.javaproperties.PropertyValue;
import com.seed4j.module.domain.javaproperties.SpringProfile;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SpringdocAuth0ModuleFactory {

  private static final SpringProfile AUTH0_SPRING_PROFILE = new SpringProfile("auth0");

  private static final String AUTH0_CLIENT_ID_PROPERTY = "auth0ClientId";
  private static final String AUTH0_DOMAIN_PROPERTY = "auth0Domain";

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .springMainProperties(AUTH0_SPRING_PROFILE)
        .set(propertyKey("springdoc.swagger-ui.oauth.client-id"), clientId(properties))
        .set(propertyKey("springdoc.swagger-ui.oauth.realm"), propertyValue("seed4j"))
        .set(propertyKey("springdoc.swagger-ui.oauth.scopes"), propertyValue("openid","profile","email"))
        .set(propertyKey("springdoc.oauth2.authorization-url"), authorizationUrl(properties))
        .and()
      .build();
    // @formatter:on
  }

  private static PropertyValue clientId(SeedModuleProperties properties) {
    return propertyValue(properties.getString(AUTH0_CLIENT_ID_PROPERTY));
  }

  private static PropertyValue authorizationUrl(SeedModuleProperties properties) {
    String auth0Domain = properties.getString(AUTH0_DOMAIN_PROPERTY);
    return propertyValue("https://%s/authorize?audience=https://%s/api/v2/".formatted(auth0Domain, auth0Domain));
  }
}
