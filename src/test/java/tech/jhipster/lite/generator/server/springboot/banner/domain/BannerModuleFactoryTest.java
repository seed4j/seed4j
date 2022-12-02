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
  @DisplayName("JHipster Banner")
  void shouldCreateModuleJHipsterBanner() {
    JHipsterModule module = factory.buildModule(testModuleProperties());

    assertThatModule(module).hasFile(BANNER_TXT).containing("█████  ██");
  }
}
