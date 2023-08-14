package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocauth0.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.moduleBuilder;
import static tech.jhipster.lite.module.domain.JHipsterModule.propertyKey;
import static tech.jhipster.lite.module.domain.JHipsterModule.propertyValue;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.javaproperties.PropertyValue;
import tech.jhipster.lite.module.domain.javaproperties.SpringProfile;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class SpringDocAuth0ModuleFactory {

  private static final SpringProfile AUTH0_SPRING_PROFILE = new SpringProfile("auth0");

  private static final String AUTH0_CLIENT_ID_PROPERTY = "auth0ClientId";
  private static final String AUTH0_DOMAIN_PROPERTY = "auth0Domain";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .springMainProperties(AUTH0_SPRING_PROFILE)
        .set(propertyKey("springdoc.swagger-ui.oauth.client-id"), clientId(properties))
        .set(propertyKey("springdoc.swagger-ui.oauth.realm"), propertyValue("jhipster"))
        .set(propertyKey("springdoc.swagger-ui.oauth.scopes"), propertyValue("openid","profile","email"))
        .set(propertyKey("springdoc.oauth2.authorization-url"), authorizationUrl(properties))
        .and()
      .build();
    //@formatter:on
  }

  private static PropertyValue clientId(JHipsterModuleProperties properties) {
    return propertyValue(properties.getString(AUTH0_CLIENT_ID_PROPERTY));
  }

  private static PropertyValue authorizationUrl(JHipsterModuleProperties properties) {
    String auth0Domain = properties.getString(AUTH0_DOMAIN_PROPERTY);
    return propertyValue(
      new StringBuilder()
        .append("https://")
        .append(auth0Domain)
        .append("/authorize?audience=https://")
        .append(auth0Domain)
        .append("/api/v2/")
        .toString()
    );
  }
}
