package com.seed4j.generator.server.pagination.jpa.domain;

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

public class JpaPaginationModuleFactory {

  private static final SeedSource SOURCE = from("server/pagination/jpa");
  private static final SeedSource MAIN_SOURCE = SOURCE.append("main");
  private static final SeedSource TEST_SOURCE = SOURCE.append("test");

  private static final String SECONDARY_DESTINATION = "shared/pagination/infrastructure/secondary";

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    String baseName = properties.projectBaseName().capitalized();

    String packagePath = properties.packagePath();

    SeedDestination testDestination = toSrcTestJava().append(packagePath).append(SECONDARY_DESTINATION);
    SeedDestination mainDestination = toSrcMainJava().append(packagePath).append(SECONDARY_DESTINATION);

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
