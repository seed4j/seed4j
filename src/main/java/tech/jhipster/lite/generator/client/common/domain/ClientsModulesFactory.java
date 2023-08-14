package tech.jhipster.lite.generator.client.common.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class ClientsModulesFactory {

  private ClientsModulesFactory() {}

  public static JHipsterModuleBuilder clientModuleBuilder(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .optionalReplacements()
      .in(path("package.json"))
      .and()
      .in(path(".lintstagedrc.js"))
      .add(lineBeforeRegex("\\['prettier --write'\\]"), "  '{src/**/,}*.{js,ts,tsx,vue}': ['eslint --fix'],")
      .and()
      .and();
  }
}
