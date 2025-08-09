package com.seed4j.module.domain.landscape;

import static com.seed4j.module.domain.JHipsterModulesFixture.*;
import static com.seed4j.module.domain.landscape.JHipsterLandscapeFixture.*;
import static com.seed4j.module.domain.landscape.JHipsterLandscapeFixture.moduleResources;
import static com.seed4j.module.domain.resource.JHipsterModuleRank.*;
import static com.seed4j.module.domain.resource.JHipsterModulesResourceFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedFeatureSlug;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class SeedLandscapeTest {

  @Test
  void shouldNotBuildWithFeatureNameConflictingWithModuleName() {
    assertThatThrownBy(() -> SeedLandscape.from(moduleResources(testModule(), testFeature())))
      .isExactlyInstanceOf(InvalidLandscapeException.class)
      .hasMessageContaining("\"test\"");
  }

  private JHipsterModuleResource testFeature() {
    return defaultModuleResourceBuilder().feature("test").build();
  }

  private JHipsterModuleResource testModule() {
    return defaultModuleResourceBuilder().slug("test").build();
  }

  @Test
  void shouldNotBuildLandscapeWithoutRootElement() {
    assertThatThrownBy(() -> SeedLandscape.from(moduleResources(defaultModuleResourceBuilder().moduleDependency("unknown").build())))
      .isExactlyInstanceOf(InvalidLandscapeException.class)
      .hasMessageContaining("root element");
  }

  @Test
  void shouldNotBuildLandscapeWithUnknownDependency() {
    assertThatThrownBy(() ->
      SeedLandscape.from(
        moduleResources(defaultModuleResource(), defaultModuleResourceBuilder().slug("dummy").moduleDependency("unknown").build())
      )
    )
      .isExactlyInstanceOf(InvalidLandscapeException.class)
      .hasMessageContaining("unknown dependency");
  }

  @Test
  void shouldNotBuildLandscapeWithLoopingDependencies() {
    JHipsterModuleResource root = defaultModuleResource();
    JHipsterModuleResource first = defaultModuleResourceBuilder().slug("first").moduleDependency("second").build();
    JHipsterModuleResource second = defaultModuleResourceBuilder().slug("second").moduleDependency("first").build();

    assertThatThrownBy(() -> SeedLandscape.from(moduleResources(root, first, second)))
      .isExactlyInstanceOf(InvalidLandscapeException.class)
      .hasMessageContaining("unknown dependency");
  }

  @Test
  void shouldBuildOneLevelLandscapeFromOneModule() {
    SeedLandscape landscape = SeedLandscape.from(moduleResources(defaultModuleResource()));

    assertThat(landscape.levels().get())
      .usingRecursiveFieldByFieldElementComparator()
      .containsExactly(landscapeLevel(noDependencyLandscapeModule("slug")));
  }

  @Test
  void shouldBuildOneLevelLandscapeFromOneFeatureWithOneModule() {
    SeedLandscape landscape = SeedLandscape.from(moduleResources(defaultModuleResourceBuilder().feature("my-feature").build()));

    assertThat(landscape.levels().get())
      .usingRecursiveFieldByFieldElementComparator()
      .containsExactly(landscapeLevel(landscapeFeature("my-feature", noDependencyLandscapeModule("slug"))));
  }

  @Test
  void shouldBuildOneLevelLandscapeFromOneFeatureWithTwoModules() {
    JHipsterModuleResource firstModule = defaultModuleResourceBuilder().slug("first").feature("my-feature").build();
    JHipsterModuleResource secondModule = defaultModuleResourceBuilder().slug("second").feature("my-feature").build();

    SeedLandscape landscape = SeedLandscape.from(moduleResources(firstModule, secondModule));

    assertThat(landscape.levels().get())
      .usingRecursiveFieldByFieldElementComparator()
      .containsExactly(
        landscapeLevel(landscapeFeature("my-feature", noDependencyLandscapeModule("first"), noDependencyLandscapeModule("second")))
      );
  }

  @Test
  void shouldBuildTwoLevelsLandscapeFromTwoModules() {
    JHipsterModuleResource firstModule = defaultModuleResourceBuilder().slug("first").build();
    JHipsterModuleResource secondModule = defaultModuleResourceBuilder().slug("second").moduleDependency("first").build();

    SeedLandscape landscape = SeedLandscape.from(moduleResources(firstModule, secondModule));

    assertThat(landscape.levels().get())
      .usingRecursiveFieldByFieldElementComparator()
      .containsExactly(
        landscapeLevel(noDependencyLandscapeModule("first")),
        landscapeLevel(oneModuleDependencyLandscapeModule("second", "first"))
      );
  }

  @Test
  void shouldBuildTwoLevelsLandscapeFromTwoModulesWithModuleDependencyInsideAFeature() {
    JHipsterModuleResource firstModule = defaultModuleResourceBuilder().slug("first").feature("root").build();
    JHipsterModuleResource secondModule = defaultModuleResourceBuilder().slug("second").moduleDependency("first").build();

    SeedLandscape landscape = SeedLandscape.from(moduleResources(firstModule, secondModule));

    assertThat(landscape.levels().get())
      .usingRecursiveFieldByFieldElementComparator()
      .containsExactly(
        landscapeLevel(landscapeFeature("root", noDependencyLandscapeModule("first"))),
        landscapeLevel(oneModuleDependencyLandscapeModule("second", "first"))
      );
  }

  @Test
  void shouldBuildThreeLevelsLandscapeFromFourModules() {
    JHipsterModuleResource firstModule = defaultModuleResourceBuilder().slug("first").build();
    JHipsterModuleResource secondModule = defaultModuleResourceBuilder().slug("second").feature("my-feature").build();
    JHipsterModuleResource thirdModule = defaultModuleResourceBuilder()
      .slug("third")
      .feature("my-feature")
      .moduleDependency("first")
      .build();
    JHipsterModuleResource forthModule = defaultModuleResourceBuilder().slug("forth").featureDependency("my-feature").build();

    SeedLandscape landscape = SeedLandscape.from(moduleResources(forthModule, secondModule, thirdModule, firstModule));

    assertThat(landscape.levels().get())
      .usingRecursiveFieldByFieldElementComparator()
      .containsExactly(
        landscapeLevel(noDependencyLandscapeModule("first")),
        landscapeLevel(
          landscapeFeature("my-feature", noDependencyLandscapeModule("second"), oneModuleDependencyLandscapeModule("third", "first"))
        ),
        landscapeLevel(
          SeedLandscapeModule.builder()
            .module("forth")
            .operation("operation")
            .propertiesDefinition(propertiesDefinition())
            .rank(RANK_D)
            .dependencies(List.of(new SeedFeatureDependency(new SeedFeatureSlug("my-feature"))))
        )
      );
  }

  @Test
  void shouldSortLevelElements() {
    JHipsterModuleResource firstModule = defaultModuleResourceBuilder().slug("root").build();
    JHipsterModuleResource secondModule = defaultModuleResourceBuilder().slug("bsecond").moduleDependency("root").build();
    JHipsterModuleResource thirdModule = defaultModuleResourceBuilder().slug("athird").moduleDependency("root").build();
    JHipsterModuleResource forthModule = defaultModuleResourceBuilder().slug("forth").moduleDependency("root").feature("feat").build();
    JHipsterModuleResource fifthModule = defaultModuleResourceBuilder().slug("fifth").moduleDependency("root").feature("feat").build();

    SeedLandscape landscape = SeedLandscape.from(moduleResources(firstModule, secondModule, thirdModule, forthModule, fifthModule));

    Iterator<SeedLandscapeLevel> iterator = landscape.levels().get().iterator();
    iterator.next();
    SeedLandscapeLevel firstLevel = iterator.next();
    assertThat(firstLevel.elements())
      .extracting(SeedLandscapeElement::slug)
      .containsExactly(moduleSlug("athird"), moduleSlug("bsecond"), featureSlug("feat"));
  }

  @Test
  void shouldRemoveNestedDependencies() {
    JHipsterModuleResource firstModule = defaultModuleResourceBuilder().slug("first").build();
    JHipsterModuleResource secondModule = defaultModuleResourceBuilder().slug("second").moduleDependency("first").build();
    JHipsterModuleResource thirdModule = defaultModuleResourceBuilder()
      .slug("third")
      .moduleDependency("second")
      .moduleDependency("first")
      .build();
    JHipsterModuleResource forthModule = defaultModuleResourceBuilder()
      .slug("forth")
      .moduleDependency("third")
      .moduleDependency("second")
      .moduleDependency("first")
      .build();

    SeedLandscape landscape = SeedLandscape.from(moduleResources(firstModule, secondModule, thirdModule, forthModule));

    assertThat(landscape.levels().get())
      .usingRecursiveFieldByFieldElementComparator()
      .containsExactly(
        landscapeLevel(noDependencyLandscapeModule("first")),
        landscapeLevel(oneModuleDependencyLandscapeModule("second", "first")),
        landscapeLevel(oneModuleDependencyLandscapeModule("third", "second")),
        landscapeLevel(oneModuleDependencyLandscapeModule("forth", "third"))
      );
  }

  @Test
  void shouldSortModuleInApplicableOrder() {
    JHipsterModuleResource firstModule = defaultModuleResourceBuilder().slug("first").build();
    JHipsterModuleResource secondModule = defaultModuleResourceBuilder().slug("second").feature("test").moduleDependency("first").build();

    SeedLandscape landscape = SeedLandscape.from(moduleResources(firstModule, secondModule));

    assertThat(landscape.sort(List.of(moduleSlug("second"), moduleSlug("first")))).containsExactly(
      moduleSlug("first"),
      moduleSlug("second")
    );
  }

  @Test
  void shouldSortInitAsFirstModule() {
    JHipsterModuleResource init = defaultModuleResourceBuilder().slug("init").build();
    JHipsterModuleResource root = defaultModuleResourceBuilder().slug("root").build();

    SeedLandscape landscape = SeedLandscape.from(moduleResources(root, init));

    assertThat(landscape.sort(List.of(moduleSlug("root"), moduleSlug("init")))).containsExactly(moduleSlug("init"), moduleSlug("root"));
  }

  @Test
  void shouldHaveMeaningfulToString() {
    assertThat(defaultModuleResource().toString()).contains("slug=", "apiDoc=", "description=");
    assertThat(noDependencyLandscapeModule("first").toString()).contains("JHipsterLandscapeModule[module=");
  }
}
