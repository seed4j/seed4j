package tech.jhipster.lite.generator.client.rest_pagination.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class TSRestPaginationFactory {

  private static final JHipsterSource SOURCE = from("client/pagination/secondary");
  private static final JHipsterDestination MAIN_DESTINATION = to("src/main/webapp/app/shared/pagination/infrastructure/secondary/");
  private static final JHipsterDestination TEST_DESTINATION = to("src/test/javascript/spec/shared/pagination/infrastructure/secondary/");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    //@formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("rest-page"), SOURCE.file("rest-page.md"))
      .files()
        .add(SOURCE.template("RestPage.ts"), MAIN_DESTINATION.append("RestPage.ts"))
        .add(SOURCE.template("RestPage.spec.ts"), TEST_DESTINATION.append("RestPage.spec.ts"))
        .and()
      .build();
    //@formatter:on
  }
}
