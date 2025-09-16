package com.seed4j.generator.client.restpagination.domain;

import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.to;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class TSRestPaginationModuleFactory {

  private static final Seed4JSource SOURCE = from("client/pagination/secondary");
  private static final Seed4JDestination MAIN_DESTINATION = to("src/main/webapp/app/shared/pagination/infrastructure/secondary/");
  private static final Seed4JDestination TEST_DESTINATION = to("src/test/webapp/unit/shared/pagination/infrastructure/secondary/");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
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
