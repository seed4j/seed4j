package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class SeedProjectNameTest {

  @Test
  void shouldGetDefaultNameFromNullName() {
    assertThat(new SeedProjectName(null).get()).isEqualTo("Seed4J Project");
  }

  @Test
  void shouldGetDefaultNameFromBlankName() {
    assertThat(new SeedProjectName(" ").get()).isEqualTo("Seed4J Project");
  }

  @Test
  void shouldGetProjectNameFromActualName() {
    assertThat(new SeedProjectName("My project").get()).isEqualTo("My project");
  }
}
