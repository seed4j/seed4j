package com.seed4j.generator.server.pagination.rest.domain;

import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.JHipsterDestination;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class RestPaginationModuleFactory {

  private static final JHipsterSource SOURCE = from("server/pagination/rest");
  private static final JHipsterSource MAIN_SOURCE = SOURCE.append("main");
  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");

  private static final String PRIMARY_DESTINATION = "shared/pagination/infrastructure/primary";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String baseName = properties.projectBaseName().capitalized();

    JHipsterDestination mainDestination = toSrcMainJava().append(properties.packagePath()).append(PRIMARY_DESTINATION);
    JHipsterDestination testDestination = toSrcTestJava().append(properties.packagePath()).append(PRIMARY_DESTINATION);

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
