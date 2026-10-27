package com.seed4j.module.domain.resource;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModuleSlug;
import com.seed4j.module.domain.landscape.Seed4JModuleDependency;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JModuleOrganizationTest {

  @Test
  @SuppressWarnings("removal")
  void shouldDependOnSpringBootWithDeprecatedOrganization() {
    Seed4JModuleOrganization organization = Seed4JModuleOrganization.SPRINGBOOT_DEPENDENCY;

    assertThat(organization.feature()).isEmpty();
    assertThat(organization.dependencies()).containsExactly(new Seed4JModuleDependency(new Seed4JModuleSlug("spring-boot")));
  }
}
