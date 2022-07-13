package tech.jhipster.lite.generator.server.springboot.banner.domain;

import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.*;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;

@UnitTest
class BannerModuleFactoryTest {

  private static final BannerModuleFactory factory = new BannerModuleFactory();
  private static final String BANNER_TXT = "src/main/resources/banner.txt";

  @Test
  @DisplayName("JHipster Banner V7")
  void shouldCreateModuleJHipsterBannerV7() {
    JHipsterModule module = factory.buildModuleBannerJHipsterV7(testModuleProperties());

    assertThatModule(module).hasFiles(BANNER_TXT);
  }

  @Test
  @DisplayName("JHipster Banner V7 React")
  void shouldCreateModuleJHipsterBannerV7React() {
    JHipsterModule module = factory.buildModuleBannerJHipsterV7React(testModuleProperties());

    assertThatModule(module).hasFiles(BANNER_TXT);
  }

  @Test
  @DisplayName("JHipster Banner V7 Vue")
  void shouldCreateModuleJHipsterBannerV7Vue() {
    JHipsterModule module = factory.buildModuleBannerJHipsterV7Vue(testModuleProperties());

    assertThatModule(module).hasFiles(BANNER_TXT);
  }

  @Test
  @DisplayName("JHipster Banner V2")
  void shouldCreateModuleJHipsterBannerV2() {
    JHipsterModule module = factory.buildModuleBannerJHipsterV2(testModuleProperties());

    assertThatModule(module).hasFiles(BANNER_TXT);
  }

  @Test
  @DisplayName("JHipster Banner V3")
  void shouldCreateModuleJHipsterBannerV3() {
    JHipsterModule module = factory.buildModuleBannerJHipsterV3(testModuleProperties());

    assertThatModule(module).hasFiles(BANNER_TXT);
  }

  @Test
  @DisplayName("Ippon Banner")
  void shouldCreateModuleIpponBanner() {
    JHipsterModule module = factory.buildModuleBannerIppon(testModuleProperties());

    assertThatModule(module).hasFiles(BANNER_TXT);
  }
}
