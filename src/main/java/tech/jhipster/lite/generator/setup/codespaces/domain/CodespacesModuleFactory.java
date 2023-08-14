package tech.jhipster.lite.generator.setup.codespaces.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class CodespacesModuleFactory {

  private static final JHipsterSource SOURCE = from("setup/codespaces");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .context()
        .put("serverPort", properties.getOrDefaultInteger("serverPort", 8080))
        .and()
      .files()
        .batch(SOURCE, to(".devcontainer"))
          .addTemplate("devcontainer.json")
          .addFile("Dockerfile")
          .and()
        .and()
      .build();
    //@formatter:on
  }
}
