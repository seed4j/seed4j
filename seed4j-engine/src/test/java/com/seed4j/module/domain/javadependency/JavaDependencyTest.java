package com.seed4j.module.domain.javadependency;

import com.seed4j.UnitTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

@UnitTest
class JavaDependencyTest {

  @Test
  void shouldRespectEqualsContract() {
    // exclusions are not merged with the project dependency: comparing them would rewrite it without its exclusions
    EqualsVerifier.forClass(JavaDependency.class).withIgnoredFields("exclusions").verify();
  }
}
