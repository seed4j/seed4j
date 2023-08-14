package tech.jhipster.lite.generator.server.pagination.domainmodel.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class PaginationDomainModuleFactory {

  private static final JHipsterSource SOURCE = from("server/pagination/domain");
  private static final JHipsterSource MAIN_SOURCE = SOURCE.append("main");
  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    JHipsterDestination mainDestination = toSrcMainJava().append(packagePath).append("shared/pagination");
    JHipsterDestination mainDomainDestination = mainDestination.append("domain");

    JHipsterDestination testDomainDestination = toSrcTestJava().append(packagePath).append("shared/pagination/domain");

    String baseName = properties.projectBaseName().capitalized();

    //@formatter:off
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
    //@formatter:on
  }
}
