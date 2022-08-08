package tech.jhipster.lite.generator.client.tikui.core.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.packagejson.VersionSource.*;

import tech.jhipster.lite.generator.client.common.domain.ClientsModulesFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class TikuiModulesFactory {

  private static final JHipsterSource SOURCE = from("client/tikui");

  public JHipsterModule buildTikuiModule(JHipsterModuleProperties properties) {
    return ClientsModulesFactory
      .clientModuleBuilder(properties)
      .packageJson()
      .addDependency(packageName("@tikui/core"), COMMON)
      .addDependency(packageName("tikuidoc-tikui"), COMMON)
      .and()
      .files()
      .add(SOURCE.file("tikuiconfig.json"), to("tikuiconfig.json"))
      .and()
      .build();
  }
}
