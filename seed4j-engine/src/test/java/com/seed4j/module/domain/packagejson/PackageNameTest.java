package com.seed4j.module.domain.packagejson;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class PackageNameTest {

  @Test
  void shouldGetValueAsString() {
    assertThat(new PackageName("vue")).hasToString("vue");
  }
}
