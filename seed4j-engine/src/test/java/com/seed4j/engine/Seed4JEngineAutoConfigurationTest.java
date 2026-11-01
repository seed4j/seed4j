package com.seed4j.engine;

import static com.seed4j.module.domain.resource.Seed4JModulesResourceFixture.defaultModuleResource;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import com.seed4j.history.infrastructure.primary.JavaProjectHistories;
import com.seed4j.module.application.Seed4JModulesApplicationService;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@UnitTest
class Seed4JEngineAutoConfigurationTest {

  @Test
  void shouldStartEngineInSpringBootApplication() {
    try (
      ConfigurableApplicationContext context = new SpringApplicationBuilder(EngineApplication.class)
        .web(WebApplicationType.NONE)
        .bannerMode(Banner.Mode.OFF)
        .logStartupInfo(false)
        .run()
    ) {
      Seed4JModulesApplicationService modules = context.getBean(Seed4JModulesApplicationService.class);

      assertThat(modules.resources().get(defaultModuleResource().slug())).isNotNull();
      assertThat(context.getBean(JavaProjectHistories.class)).isNotNull();
    }
  }

  @Test
  void shouldConfigureAllEngineComponents() {
    String[] scannedPackages = Stream.of(Seed4JEngineAutoConfiguration.class.getAnnotation(ComponentScan.class).value())
      .map(scannedPackage -> scannedPackage + "..")
      .toArray(String[]::new);

    classes()
      .that()
      .areMetaAnnotatedWith(Component.class)
      .and()
      .areNotAnnotatedWith(AutoConfiguration.class)
      .should()
      .resideInAnyPackage(scannedPackages)
      .because("The engine components are only available to the applications through its auto-configuration")
      .check(new ClassFileImporter().withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS).importPackages("com.seed4j"));
  }

  @SpringBootConfiguration
  @EnableAutoConfiguration
  static class EngineApplication {}
}
