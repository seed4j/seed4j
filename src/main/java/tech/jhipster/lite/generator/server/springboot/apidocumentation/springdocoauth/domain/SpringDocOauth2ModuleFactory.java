package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocoauth.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class SpringDocOauth2ModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/apidocumentation/springdocoauth");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .files()
        .add(
          SOURCE.template("SpringdocOAuth2Configuration.java"),
          toSrcMainJava()
            .append(properties.packagePath())
            .append("wire/springdoc/infrastructure/primary/SpringdocOAuth2Configuration.java")
        )
        .and()
      .springMainProperties()
        .set(propertyKey("springdoc.swagger-ui.oauth.client-id"), propertyValue("web_app"))
        .set(propertyKey("springdoc.swagger-ui.oauth.realm"), propertyValue("jhipster"))
        .set(
          propertyKey("springdoc.oauth2.authorization-url"),
          propertyValue("http://localhost:9080/realms/jhipster/protocol/openid-connect/auth")
        )
        .and()
      .springTestProperties()
        .set(propertyKey("springdoc.swagger-ui.oauth.client-id"), propertyValue("web_app"))
        .set(propertyKey("springdoc.swagger-ui.oauth.realm"), propertyValue("jhipster"))
        .set(
          propertyKey("springdoc.oauth2.authorization-url"),
          propertyValue("http://localhost:9080/realms/jhipster/protocol/openid-connect/auth")
        )
        .and()
      .build();
    //@formatter:on
  }
}
