package tech.jhipster.lite.generator.server.springboot.banner.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.from;
import static tech.jhipster.lite.module.domain.JHipsterModule.to;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class BannerModuleFactory {

  private static final String SOURCE_FOLDER = "server/springboot/banner";
  private static final String PROPERTIES = "properties";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);

    String projectName = properties.projectName().get();

    // @formatter:off
    return JHipsterModule
      .moduleBuilder(properties)
      .context()
        .put("banner", AsciiArtGenerator.from(projectName))
        .and()
      .files()
        .add(source().template("banner.txt"), to("src/main/resources/banner.txt"))
        .and()
      .build();
    // @formatter:on
  }

  private JHipsterSource source() {
    return from(SOURCE_FOLDER);
  }
}
