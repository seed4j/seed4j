package com.seed4j.generator.client.restpagination.domain;

import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.to;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.JHipsterDestination;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class TSRestPaginationModuleFactory {

  private static final JHipsterSource SOURCE = from("client/pagination/secondary");
  private static final JHipsterDestination MAIN_DESTINATION = to("src/main/webapp/app/shared/pagination/infrastructure/secondary/");
  private static final JHipsterDestination TEST_DESTINATION = to("src/test/webapp/unit/shared/pagination/infrastructure/secondary/");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("rest-page"), SOURCE.file("rest-page.md"))
      .files()
        .add(SOURCE.template("RestPage.ts"), MAIN_DESTINATION.append("RestPage.ts"))
        .add(SOURCE.template("RestPage.spec.ts"), TEST_DESTINATION.append("RestPage.spec.ts"))
        .and()
      .build();
    // @formatter:on
  }
}
