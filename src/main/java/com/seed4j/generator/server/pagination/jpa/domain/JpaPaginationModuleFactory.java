package com.seed4j.generator.server.pagination.jpa.domain;

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

public class JpaPaginationModuleFactory {

  private static final JHipsterSource SOURCE = from("server/pagination/jpa");
  private static final JHipsterSource MAIN_SOURCE = SOURCE.append("main");
  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");

  private static final String SECONDARY_DESTINATION = "shared/pagination/infrastructure/secondary";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String baseName = properties.projectBaseName().capitalized();

    String packagePath = properties.packagePath();

    JHipsterDestination testDestination = toSrcTestJava().append(packagePath).append(SECONDARY_DESTINATION);
    JHipsterDestination mainDestination = toSrcMainJava().append(packagePath).append(SECONDARY_DESTINATION);

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
