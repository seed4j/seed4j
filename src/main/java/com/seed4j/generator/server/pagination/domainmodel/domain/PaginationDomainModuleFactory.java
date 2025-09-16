package com.seed4j.generator.server.pagination.domainmodel.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;
import static com.seed4j.module.domain.Seed4JModule.versionSlug;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class PaginationDomainModuleFactory {

  private static final Seed4JSource SOURCE = from("server/pagination/domain");
  private static final Seed4JSource MAIN_SOURCE = SOURCE.append("main");
  private static final Seed4JSource TEST_SOURCE = SOURCE.append("test");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    Seed4JDestination mainDestination = toSrcMainJava().append(packagePath).append("shared/pagination");
    Seed4JDestination mainDomainDestination = mainDestination.append("domain");

    Seed4JDestination testDomainDestination = toSrcTestJava().append(packagePath).append("shared/pagination/domain");

    String baseName = properties.projectBaseName().capitalized();

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("baseName", baseName)
        .and()
      .javaDependencies()
        .addDependency(groupId("org.apache.commons"), artifactId("commons-lang3"), versionSlug("commons-lang3"))
        .and()
      .files()
        .add(SOURCE.template("package-info.java.mustache"), mainDestination.append("package-info.java"))
        .add(MAIN_SOURCE.template("AppPage.java"), mainDomainDestination.append(baseName + "Page.java"))
        .add(MAIN_SOURCE.template("AppPageable.java"), mainDomainDestination.append(baseName + "Pageable.java"))
        .add(TEST_SOURCE.template("AppPageTest.java"), testDomainDestination.append(baseName + "PageTest.java"))
        .add(TEST_SOURCE.template("AppPageableTest.java"), testDomainDestination.append(baseName + "PageableTest.java"))
        .add(TEST_SOURCE.template("AppPagesFixture.java"), testDomainDestination.append(baseName + "PagesFixture.java"))
        .and()
      .build();
    // @formatter:on
  }
}
