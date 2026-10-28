package com.seed4j.module.domain.javabuild;

import com.seed4j.UnitTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

@UnitTest
class MavenBuildExtensionTest {

  @Test
  void shouldRespectEqualsContract() {
    EqualsVerifier.forClass(MavenBuildExtension.class).verify();
  }
}
