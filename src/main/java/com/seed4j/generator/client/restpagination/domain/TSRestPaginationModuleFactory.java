package com.seed4j.generator.client.restpagination.domain;

import static com.seed4j.module.domain.SeedModule.documentationTitle;
import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.to;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class TSRestPaginationModuleFactory {

  private static final SeedSource SOURCE = from("client/pagination/secondary");
  private static final SeedDestination MAIN_DESTINATION = to("src/main/webapp/app/shared/pagination/infrastructure/secondary/");
  private static final SeedDestination TEST_DESTINATION = to("src/test/webapp/unit/shared/pagination/infrastructure/secondary/");

  public SeedModule buildModule(SeedModuleProperties properties) {
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
