package com.seed4j.module.domain.javadependency;

import static com.seed4j.module.domain.Seed4JEngineFixture.springBootDependencyId;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

@UnitTest
class DependencyIdTest {

  @Test
  void shouldRespectEqualsContract() {
    EqualsVerifier.forClass(DependencyId.class).verify();
  }

  @Test
  void shouldGetIdentifiersAsString() {
    assertThat(springBootDependencyId().toString()).contains("groupId=org.springframework.boot", "artifactId=spring-boot-dependencies");
  }
}
