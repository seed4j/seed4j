package com.seed4j.generator.server.springboot.apidocumentation.springdocoauth2.domain;

import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.propertyKey;
import static com.seed4j.module.domain.SeedModule.propertyValue;
import static com.seed4j.module.domain.SeedModule.toSrcMainJava;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.util.regex.Pattern;

public class SpringdocOauth2ModuleFactory {

  public static final String REALM_NAME = "keycloakRealmName";
  public static final String DEFAULT_REALM_NAME = "seed4j";
  private static final Pattern NAME_FORMAT = Pattern.compile("^[a-z0-9-]+$");

  private static final SeedSource SOURCE = from("server/springboot/apidocumentation/springdocoauth");

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    String realmName = properties.getOrDefaultString(REALM_NAME, DEFAULT_REALM_NAME);
    Assert.field(REALM_NAME, realmName).notNull().matchesPattern(NAME_FORMAT).maxLength(30);

    // @formatter:off
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
        .set(propertyKey("springdoc.swagger-ui.oauth.realm"), propertyValue(realmName))
        .set(
          propertyKey("springdoc.oauth2.authorization-url"),
          propertyValue("http://localhost:9080/realms/" + realmName + "/protocol/openid-connect/auth")
        )
        .and()
      .springTestProperties()
        .set(propertyKey("springdoc.swagger-ui.oauth.client-id"), propertyValue("web_app"))
        .set(propertyKey("springdoc.swagger-ui.oauth.realm"), propertyValue(realmName))
        .set(
          propertyKey("springdoc.oauth2.authorization-url"),
          propertyValue("http://localhost:9080/realms/" + realmName + "/protocol/openid-connect/auth")
        )
        .and()
      .build();
    // @formatter:on
  }
}
