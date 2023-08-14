package tech.jhipster.lite.generator.server.springboot.banner.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class BannerModuleFactory {

  private static final String SOURCE_FOLDER = "server/springboot/banner";
  private static final String PROPERTIES = "properties";

  public JHipsterModule buildModuleBannerJHipsterV7(JHipsterModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);
    return buildModuleBanner(properties, "banner-jhipster-v7.txt");
  }

  public JHipsterModule buildModuleBannerJHipsterV7React(JHipsterModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);
    return buildModuleBanner(properties, "banner-jhipster-v7-react.txt");
  }

  public JHipsterModule buildModuleBannerJHipsterV7Vue(JHipsterModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);
    return buildModuleBanner(properties, "banner-jhipster-v7-vue.txt");
  }

  public JHipsterModule buildModuleBannerJHipsterV2(JHipsterModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);
    return buildModuleBanner(properties, "banner-jhipster-v2.txt");
  }

  public JHipsterModule buildModuleBannerJHipsterV3(JHipsterModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);
    return buildModuleBanner(properties, "banner-jhipster-v3.txt");
  }

  private JHipsterModule buildModuleBanner(JHipsterModuleProperties properties, String file) {
    // @formatter:off
    return JHipsterModule
      .moduleBuilder(properties)
      .files()
        .add(source().file(file), destination())
        .and()
      .build();
    // @formatter:on
  }

  private JHipsterSource source() {
    return from(SOURCE_FOLDER);
  }

  private JHipsterDestination destination() {
    return new JHipsterDestination("src/main/resources").append("banner.txt");
  }
}
