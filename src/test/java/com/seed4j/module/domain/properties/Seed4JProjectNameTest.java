package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JProjectNameTest {

  @Test
  void shouldGetDefaultNameFromBlankName() {
    assertThat(new Seed4JProjectName(" ").get()).isEqualTo("Seed4J Project");
  }

  @Test
  void shouldGetProjectNameFromActualName() {
    assertThat(new Seed4JProjectName("My project").get()).isEqualTo("My project");
  }
}
