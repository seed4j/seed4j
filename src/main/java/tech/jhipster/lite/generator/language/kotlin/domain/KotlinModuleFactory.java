package tech.jhipster.lite.generator.language.kotlin.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.moduleBuilder;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.gradleplugin.GradleCorePlugin;
import tech.jhipster.lite.module.domain.gradleplugin.GradlePluginId;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class KotlinModuleFactory {

  public JHipsterModule buildKotlinLanguageModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .gitIgnore()
      .comment("Kotlin Language")
      .and()
      .gradlePlugins()
      .plugin(kotlinPluginManagement())
      .and()
      .build();
    //@formatter:on
  }

  private GradleCorePlugin kotlinPluginManagement() {
    return GradleCorePlugin.builder().id(new GradlePluginId("kotlin(\"jvm\") version \"1.9.25\"")).build();
  }
}
