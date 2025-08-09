package com.seed4j.generator.server.javatool.approvaltesting.domain;

import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;
import static com.seed4j.module.domain.JHipsterModule.versionSlug;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class ApprovalTestingModuleFactory {

  private static final SeedSource SOURCE = from("server/javatool/approvaltesting");

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    SeedDestination testDestination = toSrcTestJava().append(packagePath);

    // @formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Approval Testing"), SOURCE.append("approval-testing.md"))
      .javaDependencies()
        .addTestDependency(groupId("com.approvaltests"), artifactId("approvaltests"), versionSlug("approvaltests"))
        .and()
      .files()
        .add(SOURCE.append("test").template("PackageSettings.java"), testDestination.append("PackageSettings.java"))
        .and()
      .gitIgnore()
        .comment("Approval Testing")
        .pattern("src/test/resources/**/*.received.txt")
        .and()
      .build();
    // @formatter:on
  }
}
