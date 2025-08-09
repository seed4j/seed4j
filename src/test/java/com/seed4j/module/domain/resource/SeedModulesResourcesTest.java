package com.seed4j.module.domain.resource;

import static com.seed4j.module.domain.resource.SeedModulesResourceFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModuleSlug;
import com.seed4j.shared.error.domain.MissingMandatoryValueException;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class SeedModulesResourcesTest {

  @Test
  void shouldNotGetModuleForUnknownSlug() {
    SeedModulesResources seedModulesResources = new SeedModulesResources(
      List.of(defaultModuleResourceBuilder().slug("dummy").build()),
      emptyHiddenModules()
    );

    assertThatThrownBy(() -> seedModulesResources.get(new SeedModuleSlug("dummy-2")))
      .isExactlyInstanceOf(UnknownSlugException.class)
      .hasMessageContaining("Module dummy-2 does not exist");
  }

  @Test
  void shouldNotBuildWithoutResources() {
    assertThatThrownBy(() -> new SeedModulesResources(List.of(), emptyHiddenModules()))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("modulesResources");
  }

  @Test
  void shouldNotBuildWithDuplicatedSlug() {
    SeedModuleResource resource = defaultModuleResourceBuilder().slug("dummy").build();

    assertThatThrownBy(() -> new SeedModulesResources(List.of(resource, resource), emptyHiddenModules())).isExactlyInstanceOf(
      DuplicatedSlugException.class
    );
  }

  @Test
  void shouldHaveMeaningfulToString() {
    var resource = defaultModuleResourceBuilder().slug("dummy").build();

    assertThat(resource.toString()).contains("SeedModuleResource[", "slug=dummy");
  }

  @Test
  void shouldBuildWithRankedResources() {
    var resource = defaultModuleResourceBuilder().slug("init").build();

    assertThat(resource.rank()).isEqualTo(SeedModuleRank.RANK_S);
  }

  @Test
  void shouldBuildWithDefaultRankedResources() {
    var resource = defaultModuleResourceBuilder().build();

    assertThat(resource.rank()).isEqualTo(SeedModuleRank.RANK_D);
  }
}
