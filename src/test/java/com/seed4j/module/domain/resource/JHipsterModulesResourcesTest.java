package com.seed4j.module.domain.resource;

import static com.seed4j.module.domain.resource.JHipsterModulesResourceFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModuleSlug;
import com.seed4j.shared.error.domain.MissingMandatoryValueException;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class JHipsterModulesResourcesTest {

  @Test
  void shouldNotGetModuleForUnknownSlug() {
    JHipsterModulesResources jHipsterModulesResources = new JHipsterModulesResources(
      List.of(defaultModuleResourceBuilder().slug("dummy").build()),
      emptyHiddenModules()
    );

    assertThatThrownBy(() -> jHipsterModulesResources.get(new JHipsterModuleSlug("dummy-2")))
      .isExactlyInstanceOf(UnknownSlugException.class)
      .hasMessageContaining("Module dummy-2 does not exist");
  }

  @Test
  void shouldNotBuildWithoutResources() {
    assertThatThrownBy(() -> new JHipsterModulesResources(List.of(), emptyHiddenModules()))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("modulesResources");
  }

  @Test
  void shouldNotBuildWithDuplicatedSlug() {
    JHipsterModuleResource resource = defaultModuleResourceBuilder().slug("dummy").build();

    assertThatThrownBy(() -> new JHipsterModulesResources(List.of(resource, resource), emptyHiddenModules())).isExactlyInstanceOf(
      DuplicatedSlugException.class
    );
  }

  @Test
  void shouldHaveMeaningfulToString() {
    var resource = defaultModuleResourceBuilder().slug("dummy").build();

    assertThat(resource.toString()).contains("JHipsterModuleResource[", "slug=dummy");
  }

  @Test
  void shouldBuildWithRankedResources() {
    var resource = defaultModuleResourceBuilder().slug("init").build();

    assertThat(resource.rank()).isEqualTo(JHipsterModuleRank.RANK_S);
  }

  @Test
  void shouldBuildWithDefaultRankedResources() {
    var resource = defaultModuleResourceBuilder().build();

    assertThat(resource.rank()).isEqualTo(JHipsterModuleRank.RANK_D);
  }
}
