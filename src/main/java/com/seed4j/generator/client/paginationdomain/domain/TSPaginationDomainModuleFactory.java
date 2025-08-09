package com.seed4j.generator.client.paginationdomain.domain;

import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.to;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class TSPaginationDomainModuleFactory {

  private static final SeedSource SOURCE = from("client/pagination/domain");
  private static final SeedDestination DESTINATION = to("src/main/webapp/app/shared/pagination/domain");

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.template("Page.ts"), DESTINATION.append("Page.ts"))
        .add(SOURCE.template("DisplayedOnPage.ts"), DESTINATION.append("DisplayedOnPage.ts"))
        .and()
      .build();
    // @formatter:on
  }
}
