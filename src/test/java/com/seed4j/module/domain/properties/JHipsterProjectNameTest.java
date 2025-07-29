package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class JHipsterProjectNameTest {

  @Test
  void shouldGetDefaultNameFromNullName() {
    assertThat(new JHipsterProjectName(null).get()).isEqualTo("JHipster Project");
  }

  @Test
  void shouldGetDefaultNameFromBlankName() {
    assertThat(new JHipsterProjectName(" ").get()).isEqualTo("JHipster Project");
  }

  @Test
  void shouldGetProjectNameFromActualName() {
    assertThat(new JHipsterProjectName("My project").get()).isEqualTo("My project");
  }
}
