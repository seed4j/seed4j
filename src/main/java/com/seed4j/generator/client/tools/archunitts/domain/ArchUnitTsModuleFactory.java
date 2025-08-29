package com.seed4j.generator.client.tools.archunitts.domain;

import static com.seed4j.module.domain.SeedModule.*;
import static com.seed4j.module.domain.nodejs.SeedNodePackagesVersionSource.COMMON;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class ArchUnitTsModuleFactory {

  private static final SeedSource SOURCE = from("client/tools/archunitts");

  private static final SeedDestination APP_DESTINATION = to("src/main/webapp/app/");
  private static final SeedDestination TEST_DESTINATION = to("src/test/webapp/unit/");

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDependency(packageName("arch-unit-ts"), COMMON)
        .and()
      .files()
        .add(SOURCE.template("arch-unit-ts.json"), to("arch-unit-ts.json"))
        .add(SOURCE.template("SharedKernel.ts"), APP_DESTINATION.append("SharedKernel.ts"))
        .add(SOURCE.template("BusinessContext.ts"), APP_DESTINATION.append("BusinessContext.ts"))
        .add(SOURCE.template("BusinessContextPackageInfo.ts"), APP_DESTINATION.append("home").append("package-info.ts"))
        .add(SOURCE.template("SharedKernelPackageInfo.ts"), APP_DESTINATION.append("shared").append("package-info.ts"))
        .add(SOURCE.template("HexagonalArchTest.spec.ts"), TEST_DESTINATION.append("HexagonalArchTest.spec.ts"))
        .and()
      .build();
    // @formatter:on
  }
}
