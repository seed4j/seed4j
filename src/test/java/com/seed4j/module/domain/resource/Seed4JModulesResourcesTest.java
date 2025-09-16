package com.seed4j.module.domain.resource;

import static com.seed4j.module.domain.resource.Seed4JModulesResourceFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModuleSlug;
import com.seed4j.shared.error.domain.MissingMandatoryValueException;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JModulesResourcesTest {

  @Test
  void shouldNotGetModuleForUnknownSlug() {
    Seed4JModulesResources seed4JModulesResources = new Seed4JModulesResources(
      List.of(defaultModuleResourceBuilder().slug("dummy").build()),
      emptyHiddenModules()
    );

    assertThatThrownBy(() -> seed4JModulesResources.get(new Seed4JModuleSlug("dummy-2")))
      .isExactlyInstanceOf(UnknownSlugException.class)
      .hasMessageContaining("Module dummy-2 does not exist");
  }

  @Test
  void shouldNotBuildWithoutResources() {
    assertThatThrownBy(() -> new Seed4JModulesResources(List.of(), emptyHiddenModules()))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("modulesResources");
  }

  @Test
  void shouldNotBuildWithDuplicatedSlug() {
    Seed4JModuleResource resource = defaultModuleResourceBuilder().slug("dummy").build();

    assertThatThrownBy(() -> new Seed4JModulesResources(List.of(resource, resource), emptyHiddenModules())).isExactlyInstanceOf(
      DuplicatedSlugException.class
    );
  }

  @Test
  void shouldHaveMeaningfulToString() {
    var resource = defaultModuleResourceBuilder().slug("dummy").build();

    assertThat(resource.toString()).contains("Seed4JModuleResource[", "slug=dummy");
  }

  @Test
  void shouldBuildWithRankedResources() {
    var resource = defaultModuleResourceBuilder().slug("init").build();

    assertThat(resource.rank()).isEqualTo(Seed4JModuleRank.RANK_S);
  }

  @Test
  void shouldBuildWithDefaultRankedResources() {
    var resource = defaultModuleResourceBuilder().build();

    assertThat(resource.rank()).isEqualTo(Seed4JModuleRank.RANK_D);
  }
}
