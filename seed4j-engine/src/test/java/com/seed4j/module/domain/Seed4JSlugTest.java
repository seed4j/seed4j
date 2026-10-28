package com.seed4j.module.domain;

import com.seed4j.UnitTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JSlugTest {

  @Test
  void shouldRespectEqualsContract() {
    EqualsVerifier.forClass(Seed4JSlug.class).verify();
  }
}
