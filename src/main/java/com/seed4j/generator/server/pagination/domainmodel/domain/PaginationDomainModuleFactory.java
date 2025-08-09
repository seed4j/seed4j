package com.seed4j.generator.server.pagination.domainmodel.domain;

import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;
import static com.seed4j.module.domain.JHipsterModule.versionSlug;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class PaginationDomainModuleFactory {

  private static final SeedSource SOURCE = from("server/pagination/domain");
  private static final SeedSource MAIN_SOURCE = SOURCE.append("main");
  private static final SeedSource TEST_SOURCE = SOURCE.append("test");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    SeedDestination mainDestination = toSrcMainJava().append(packagePath).append("shared/pagination");
    SeedDestination mainDomainDestination = mainDestination.append("domain");

    SeedDestination testDomainDestination = toSrcTestJava().append(packagePath).append("shared/pagination/domain");

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
