package com.seed4j.generator.server.pagination.jpa.domain;

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

public class JpaPaginationModuleFactory {

  private static final Seed4JSource SOURCE = from("server/pagination/jpa");
  private static final Seed4JSource MAIN_SOURCE = SOURCE.append("main");
  private static final Seed4JSource TEST_SOURCE = SOURCE.append("test");

  private static final String SECONDARY_DESTINATION = "shared/pagination/infrastructure/secondary";

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String baseName = properties.projectBaseName().capitalized();

    String packagePath = properties.packagePath();

    Seed4JDestination testDestination = toSrcTestJava().append(packagePath).append(SECONDARY_DESTINATION);
    Seed4JDestination mainDestination = toSrcMainJava().append(packagePath).append(SECONDARY_DESTINATION);

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("baseName", baseName)
        .and()
      .documentation(documentationTitle("Jpa pages"), SOURCE.template("jpa-pages.md"))
      .files()
        .add(MAIN_SOURCE.template("AppPages.java"), mainDestination.append(baseName + "Pages.java"))
        .add(TEST_SOURCE.template("AppPagesTest.java"), testDestination.append(baseName + "PagesTest.java"))
        .and()
      .build();
    // @formatter:on
  }
}
