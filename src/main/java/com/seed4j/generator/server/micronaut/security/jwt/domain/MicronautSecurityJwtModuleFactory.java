package com.seed4j.generator.server.micronaut.security.jwt.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class MicronautSecurityJwtModuleFactory {

  private static final Seed4JSource SOURCE = from("server/micronaut/security/jwt");
  private static final GroupId MICRONAUT_SECURITY_GROUP = groupId("io.micronaut.security");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    Seed4JDestination mainDestination = toSrcMainJava().append(packagePath).append("account/infrastructure/primary/");

    return moduleBuilder(properties)
      .files()
      .add(SOURCE.template("SecurityConfiguration.java"), mainDestination.append("SecurityConfiguration.java"))
      .and()
      .javaDependencies()
      .addDependency(MICRONAUT_SECURITY_GROUP, artifactId("micronaut-security-jwt"))
      .and()
      .springMainProperties()
      .set(propertyKey("micronaut.security.authentication"), propertyValue("bearer"))
      .set(
        propertyKey("micronaut.security.token.jwt.signatures.secret.generator.secret"),
        propertyValue("\"${JWT_SECRET:pleaseChangeThisSecretForANewOne}\"")
      )
      .set(propertyKey("micronaut.security.token.jwt.generator.access-token.expiration"), propertyValue(86400))
      .and()
      .build();
  }
}
