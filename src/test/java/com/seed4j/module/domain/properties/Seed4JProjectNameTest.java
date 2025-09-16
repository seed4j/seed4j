package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JProjectNameTest {

  @Test
  void shouldGetDefaultNameFromNullName() {
    assertThat(new Seed4JProjectName(null).get()).isEqualTo("Seed4J Project");
  }

  @Test
  void shouldGetDefaultNameFromBlankName() {
    assertThat(new Seed4JProjectName(" ").get()).isEqualTo("Seed4J Project");
  }

  @Test
  void shouldGetProjectNameFromActualName() {
    assertThat(new Seed4JProjectName("My project").get()).isEqualTo("My project");
  }
}
