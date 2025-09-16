package com.seed4j.generator.client.paginationdomain.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.to;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class TSPaginationDomainModuleFactory {

  private static final Seed4JSource SOURCE = from("client/pagination/domain");
  private static final Seed4JDestination DESTINATION = to("src/main/webapp/app/shared/pagination/domain");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
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
