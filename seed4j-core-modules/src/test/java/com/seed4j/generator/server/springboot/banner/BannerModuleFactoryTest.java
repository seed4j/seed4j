package com.seed4j.generator.server.springboot.banner;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModule;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class BannerModuleFactoryTest {

  private static final BannerModuleFactory factory = new BannerModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("myApp")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFile("src/main/resources/banner.txt")
      .containing(
        """

          ${AnsiColor.GREEN}███╗   ███╗${AnsiColor.RED}██╗   ██╗   ${AnsiColor.GREEN} █████╗ ${AnsiColor.RED}██████╗ ██████╗
          ${AnsiColor.GREEN}████╗ ████║${AnsiColor.RED}╚██╗ ██╔╝   ${AnsiColor.GREEN}██╔══██╗${AnsiColor.RED}██╔══██╗██╔══██╗
          ${AnsiColor.GREEN}██╔████╔██║${AnsiColor.RED} ╚████╔╝    ${AnsiColor.GREEN}███████║${AnsiColor.RED}██████╔╝██████╔╝
          ${AnsiColor.GREEN}██║╚██╔╝██║${AnsiColor.RED}  ╚██╔╝     ${AnsiColor.GREEN}██╔══██║${AnsiColor.RED}██╔═══╝ ██╔═══╝
          ${AnsiColor.GREEN}██║ ╚═╝ ██║${AnsiColor.RED}   ██║      ${AnsiColor.GREEN}██║  ██║${AnsiColor.RED}██║     ██║
          ${AnsiColor.GREEN}╚═╝     ╚═╝${AnsiColor.RED}   ╚═╝      ${AnsiColor.GREEN}╚═╝  ╚═╝${AnsiColor.RED}╚═╝     ╚═╝

        ${AnsiColor.BRIGHT_BLUE}:: myApp :: Running Spring Boot ${spring-boot.version} :: Startup profile(s) ${spring.profiles.active:default} ::${AnsiColor.DEFAULT}
        """
      );
  }
}
