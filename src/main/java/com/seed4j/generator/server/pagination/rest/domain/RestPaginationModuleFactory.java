package com.seed4j.generator.server.pagination.rest.domain;

import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class RestPaginationModuleFactory {

  private static final Seed4JSource SOURCE = from("server/pagination/rest");
  private static final Seed4JSource MAIN_SOURCE = SOURCE.append("main");
  private static final Seed4JSource TEST_SOURCE = SOURCE.append("test");

  private static final String PRIMARY_DESTINATION = "shared/pagination/infrastructure/primary";

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String baseName = properties.projectBaseName().capitalized();

    Seed4JDestination mainDestination = toSrcMainJava().append(properties.packagePath()).append(PRIMARY_DESTINATION);
    Seed4JDestination testDestination = toSrcTestJava().append(properties.packagePath()).append(PRIMARY_DESTINATION);

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
