package com.seed4j.module.domain;

import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModulesFixture.testModuleProperties;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.shared.error.domain.MissingMandatoryValueException;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JModuleTest {

  @Test
  @SuppressWarnings("NullAway")
  void shouldNotCustomizeBuilderWithoutCustomizer() {
    Seed4JModuleBuilder builder = moduleBuilder(testModuleProperties());

    assertThatThrownBy(() -> builder.with(null)).isExactlyInstanceOf(MissingMandatoryValueException.class);
  }

  @Test
  void shouldCustomizeBuilderWith() {
    Seed4JModuleBuilder builder = moduleBuilder(testModuleProperties());
    AtomicReference<Seed4JModuleBuilder> customized = new AtomicReference<>();

    assertThat(builder.with(customized::set)).isSameAs(builder);
    assertThat(customized).hasValue(builder);
  }

  @Test
  @SuppressWarnings("removal")
  void shouldCustomizeBuilderWithDeprecatedApply() {
    Seed4JModuleBuilder builder = moduleBuilder(testModuleProperties());
    AtomicReference<Seed4JModuleBuilder> customized = new AtomicReference<>();

    assertThat(builder.apply(customized::set)).isSameAs(builder);
    assertThat(customized).hasValue(builder);
  }
}
