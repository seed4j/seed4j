package com.seed4j.generator.client.paginationdomain.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.to;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.JHipsterDestination;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class TSPaginationDomainModuleFactory {

  private static final JHipsterSource SOURCE = from("client/pagination/domain");
  private static final JHipsterDestination DESTINATION = to("src/main/webapp/app/shared/pagination/domain");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
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
