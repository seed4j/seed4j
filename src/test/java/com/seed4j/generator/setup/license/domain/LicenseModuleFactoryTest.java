package com.seed4j.generator.setup.license.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import java.time.Year;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;

@UnitTest
class LicenseModuleFactoryTest {

  private static final LicenseModuleFactory factory = new LicenseModuleFactory();

  @Test
  void shouldBuildMitModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();
    int year = Year.now(ZoneId.systemDefault()).getValue();

    SeedModule module = factory.buildMitModule(properties);

    assertThatModuleWithFiles(module).hasFile("LICENSE.txt").containing("Copyright %d".formatted(year));
  }

  @Test
  void shouldBuildApacheModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    SeedModule module = factory.buildApacheModule(properties);

    assertThatModuleWithFiles(module).hasFile("LICENSE.txt").containing("Apache License");
  }
}
