package com.seed4j.generator.server.springboot.common;

import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModulesFixture.testModuleProperties;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.module.domain.javaproperties.ApplicationPropertiesFile;
import com.seed4j.module.domain.javaproperties.ApplicationProperty;
import com.seed4j.module.domain.javaproperties.Seed4JModuleApplicationProperties.Seed4JModuleApplicationPropertiesBuilder;
import com.seed4j.module.domain.javaproperties.SpringProfile;
import java.util.function.Function;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
@SuppressWarnings("removal")
class SpringBootPropertiesTest {

  @Test
  void shouldWriteMainPropertiesInConfigFolderFirst() {
    assertThat(SpringBootProperties.SPRING_MAIN.folders()).containsExactly("src/main/resources/config/", "src/main/resources/");
    assertThat(SpringBootProperties.SPRING_MAIN.filename()).isEqualTo("application");
  }

  @Test
  void shouldWriteTestPropertiesWithTestProfile() {
    assertThat(SpringBootProperties.SPRING_TEST.folders()).containsExactly("src/test/resources/config/", "src/test/resources/");
    assertThat(SpringBootProperties.SPRING_TEST.filename()).isEqualTo("application-test");
  }

  @Nested
  class DeprecatedSpringPropertiesMethods {

    @Test
    void shouldWriteSpringMainPropertiesToMainFile() {
      assertSameFile(Seed4JModuleBuilder::springMainProperties, SpringBootProperties.SPRING_MAIN);
    }

    @Test
    void shouldWriteSpringMainPropertiesWithDefaultProfileToMainFile() {
      assertSameFile(builder -> builder.springMainProperties(SpringProfile.DEFAULT), SpringBootProperties.SPRING_MAIN);
    }

    @Test
    void shouldWriteSpringMainPropertiesWithBlankProfileToMainFile() {
      assertSameFile(builder -> builder.springMainProperties(new SpringProfile(" ")), SpringBootProperties.SPRING_MAIN);
    }

    @Test
    void shouldWriteSpringMainPropertiesWithProfileToProfiledMainFile() {
      assertSameFile(
        builder -> builder.springMainProperties(Seed4JModule.springProfile("okta")),
        SpringBootProperties.SPRING_MAIN.profile("okta")
      );
    }

    @Test
    void shouldWriteSpringLocalPropertiesToLocalFile() {
      assertSameFile(Seed4JModuleBuilder::springLocalProperties, SpringBootProperties.SPRING_LOCAL);
    }

    @Test
    void shouldWriteSpringMainBootstrapPropertiesToMainBootstrapFile() {
      assertSameFile(Seed4JModuleBuilder::springMainBootstrapProperties, SpringBootProperties.SPRING_MAIN_BOOTSTRAP);
    }

    @Test
    void shouldWriteSpringMainBootstrapPropertiesWithProfileToProfiledMainBootstrapFile() {
      assertSameFile(
        builder -> builder.springMainBootstrapProperties(SpringProfile.LOCAL),
        SpringBootProperties.SPRING_MAIN_BOOTSTRAP.profile("local")
      );
    }

    @Test
    void shouldWriteSpringTestPropertiesToTestFile() {
      assertSameFile(Seed4JModuleBuilder::springTestProperties, SpringBootProperties.SPRING_TEST);
    }

    @Test
    void shouldWriteSpringTestPropertiesWithProfileToProfiledTestFile() {
      assertSameFile(builder -> builder.springTestProperties(SpringProfile.LOCAL), SpringBootProperties.SPRING_TEST.profile("local"));
    }

    @Test
    void shouldWriteSpringTestBootstrapPropertiesToTestBootstrapFile() {
      assertSameFile(Seed4JModuleBuilder::springTestBootstrapProperties, SpringBootProperties.SPRING_TEST_BOOTSTRAP);
    }

    private void assertSameFile(
      Function<Seed4JModuleBuilder, Seed4JModuleApplicationPropertiesBuilder> deprecated,
      ApplicationPropertiesFile file
    ) {
      assertThat(propertiesFiles(deprecated)).containsExactly(file);
    }

    private ApplicationPropertiesFile[] propertiesFiles(
      Function<Seed4JModuleBuilder, Seed4JModuleApplicationPropertiesBuilder> properties
    ) {
      Seed4JModule module = properties
        .apply(moduleBuilder(testModuleProperties()))
        .set(propertyKey("seed4j.key"), propertyValue("value"))
        .and()
        .build();

      return module
        .applicationProperties()
        .get()
        .stream()
        .map(ApplicationProperty::file)
        .toArray(ApplicationPropertiesFile[]::new);
    }
  }
}
