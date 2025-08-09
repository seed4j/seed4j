package com.seed4j.generator.server.pagination.rest.domain;

import static com.seed4j.module.domain.SeedModule.documentationTitle;
import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.toSrcMainJava;
import static com.seed4j.module.domain.SeedModule.toSrcTestJava;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class RestPaginationModuleFactory {

  private static final SeedSource SOURCE = from("server/pagination/rest");
  private static final SeedSource MAIN_SOURCE = SOURCE.append("main");
  private static final SeedSource TEST_SOURCE = SOURCE.append("test");

  private static final String PRIMARY_DESTINATION = "shared/pagination/infrastructure/primary";

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    String baseName = properties.projectBaseName().capitalized();

    SeedDestination mainDestination = toSrcMainJava().append(properties.packagePath()).append(PRIMARY_DESTINATION);
    SeedDestination testDestination = toSrcTestJava().append(properties.packagePath()).append(PRIMARY_DESTINATION);

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("baseName", baseName)
        .and()
      .documentation(documentationTitle("Rest pagination"), SOURCE.template("rest-pagination.md"))
      .files()
        .add(MAIN_SOURCE.template("RestAppPage.java"), mainDestination.append("Rest" + baseName + "Page.java"))
        .add(MAIN_SOURCE.template("RestAppPageable.java"), mainDestination.append("Rest" + baseName + "Pageable.java"))
        .add(TEST_SOURCE.template("RestAppPageTest.java"), testDestination.append("Rest" + baseName + "PageTest.java"))
        .add(TEST_SOURCE.template("RestAppPageableTest.java"), testDestination.append("Rest" + baseName + "PageableTest.java"))
        .and()
      .build();
    // @formatter:on
  }
}
