package com.seed4j.module.domain.landscape;

import static com.seed4j.module.domain.Seed4JModulesFixture.*;
import static com.seed4j.module.domain.landscape.Seed4JLandscapeFixture.*;
import static com.seed4j.module.domain.landscape.Seed4JLandscapeFixture.moduleResources;
import static com.seed4j.module.domain.resource.Seed4JModuleRank.*;
import static com.seed4j.module.domain.resource.Seed4JModulesResourceFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JFeatureSlug;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JLandscapeTest {

  @Test
  void shouldNotBuildWithFeatureNameConflictingWithModuleName() {
    assertThatThrownBy(() -> Seed4JLandscape.from(moduleResources(testModule(), testFeature())))
      .isExactlyInstanceOf(InvalidLandscapeException.class)
      .hasMessageContaining("\"test\"");
  }

  private Seed4JModuleResource testFeature() {
    return defaultModuleResourceBuilder().feature("test").build();
  }

  private Seed4JModuleResource testModule() {
    return defaultModuleResourceBuilder().slug("test").build();
  }

  @Test
  void shouldNotBuildLandscapeWithoutRootElement() {
    assertThatThrownBy(() -> Seed4JLandscape.from(moduleResources(defaultModuleResourceBuilder().moduleDependency("unknown").build())))
      .isExactlyInstanceOf(InvalidLandscapeException.class)
      .hasMessageContaining("root element");
  }

  @Test
  void shouldNotBuildLandscapeWithUnknownDependency() {
    assertThatThrownBy(() ->
      Seed4JLandscape.from(
        moduleResources(defaultModuleResource(), defaultModuleResourceBuilder().slug("dummy").moduleDependency("unknown").build())
      )
    )
      .isExactlyInstanceOf(InvalidLandscapeException.class)
      .hasMessageContaining("unknown dependency");
  }

  @Test
  void shouldNotBuildLandscapeWithLoopingDependencies() {
    Seed4JModuleResource root = defaultModuleResource();
    Seed4JModuleResource first = defaultModuleResourceBuilder().slug("first").moduleDependency("second").build();
    Seed4JModuleResource second = defaultModuleResourceBuilder().slug("second").moduleDependency("first").build();

    assertThatThrownBy(() -> Seed4JLandscape.from(moduleResources(root, first, second)))
      .isExactlyInstanceOf(InvalidLandscapeException.class)
      .hasMessageContaining("unknown dependency");
  }

  @Test
  void shouldBuildOneLevelLandscapeFromOneModule() {
    Seed4JLandscape landscape = Seed4JLandscape.from(moduleResources(defaultModuleResource()));

    assertThat(landscape.levels().get())
      .usingRecursiveFieldByFieldElementComparator()
      .containsExactly(landscapeLevel(noDependencyLandscapeModule("slug")));
  }

  @Test
  void shouldBuildOneLevelLandscapeFromOneFeatureWithOneModule() {
    Seed4JLandscape landscape = Seed4JLandscape.from(moduleResources(defaultModuleResourceBuilder().feature("my-feature").build()));

    assertThat(landscape.levels().get())
      .usingRecursiveFieldByFieldElementComparator()
      .containsExactly(landscapeLevel(landscapeFeature("my-feature", noDependencyLandscapeModule("slug"))));
  }

  @Test
  void shouldBuildOneLevelLandscapeFromOneFeatureWithTwoModules() {
    Seed4JModuleResource firstModule = defaultModuleResourceBuilder().slug("first").feature("my-feature").build();
    Seed4JModuleResource secondModule = defaultModuleResourceBuilder().slug("second").feature("my-feature").build();

    Seed4JLandscape landscape = Seed4JLandscape.from(moduleResources(firstModule, secondModule));

    assertThat(landscape.levels().get())
      .usingRecursiveFieldByFieldElementComparator()
      .containsExactly(
        landscapeLevel(landscapeFeature("my-feature", noDependencyLandscapeModule("first"), noDependencyLandscapeModule("second")))
      );
  }

  @Test
  void shouldBuildTwoLevelsLandscapeFromTwoModules() {
    Seed4JModuleResource firstModule = defaultModuleResourceBuilder().slug("first").build();
    Seed4JModuleResource secondModule = defaultModuleResourceBuilder().slug("second").moduleDependency("first").build();

    Seed4JLandscape landscape = Seed4JLandscape.from(moduleResources(firstModule, secondModule));

    assertThat(landscape.levels().get())
      .usingRecursiveFieldByFieldElementComparator()
      .containsExactly(
        landscapeLevel(noDependencyLandscapeModule("first")),
        landscapeLevel(oneModuleDependencyLandscapeModule("second", "first"))
      );
  }

  @Test
  void shouldBuildTwoLevelsLandscapeFromTwoModulesWithModuleDependencyInsideAFeature() {
    Seed4JModuleResource firstModule = defaultModuleResourceBuilder().slug("first").feature("root").build();
    Seed4JModuleResource secondModule = defaultModuleResourceBuilder().slug("second").moduleDependency("first").build();

    Seed4JLandscape landscape = Seed4JLandscape.from(moduleResources(firstModule, secondModule));

    assertThat(landscape.levels().get())
      .usingRecursiveFieldByFieldElementComparator()
      .containsExactly(
        landscapeLevel(landscapeFeature("root", noDependencyLandscapeModule("first"))),
        landscapeLevel(oneModuleDependencyLandscapeModule("second", "first"))
      );
  }

  @Test
  void shouldBuildThreeLevelsLandscapeFromFourModules() {
    Seed4JModuleResource firstModule = defaultModuleResourceBuilder().slug("first").build();
    Seed4JModuleResource secondModule = defaultModuleResourceBuilder().slug("second").feature("my-feature").build();
    Seed4JModuleResource thirdModule = defaultModuleResourceBuilder().slug("third").feature("my-feature").moduleDependency("first").build();
    Seed4JModuleResource forthModule = defaultModuleResourceBuilder().slug("forth").featureDependency("my-feature").build();

    Seed4JLandscape landscape = Seed4JLandscape.from(moduleResources(forthModule, secondModule, thirdModule, firstModule));

    assertThat(landscape.levels().get())
      .usingRecursiveFieldByFieldElementComparator()
      .containsExactly(
        landscapeLevel(noDependencyLandscapeModule("first")),
        landscapeLevel(
          landscapeFeature("my-feature", noDependencyLandscapeModule("second"), oneModuleDependencyLandscapeModule("third", "first"))
        ),
        landscapeLevel(
          Seed4JLandscapeModule.builder()
            .module("forth")
            .operation("operation")
            .propertiesDefinition(propertiesDefinition())
            .rank(RANK_D)
            .dependencies(List.of(new Seed4JFeatureDependency(new Seed4JFeatureSlug("my-feature"))))
        )
      );
  }

  @Test
  void shouldSortLevelElements() {
    Seed4JModuleResource firstModule = defaultModuleResourceBuilder().slug("root").build();
    Seed4JModuleResource secondModule = defaultModuleResourceBuilder().slug("bsecond").moduleDependency("root").build();
    Seed4JModuleResource thirdModule = defaultModuleResourceBuilder().slug("athird").moduleDependency("root").build();
    Seed4JModuleResource forthModule = defaultModuleResourceBuilder().slug("forth").moduleDependency("root").feature("feat").build();
    Seed4JModuleResource fifthModule = defaultModuleResourceBuilder().slug("fifth").moduleDependency("root").feature("feat").build();

    Seed4JLandscape landscape = Seed4JLandscape.from(moduleResources(firstModule, secondModule, thirdModule, forthModule, fifthModule));

    Iterator<Seed4JLandscapeLevel> iterator = landscape.levels().get().iterator();
    iterator.next();
    Seed4JLandscapeLevel firstLevel = iterator.next();
    assertThat(firstLevel.elements())
      .extracting(Seed4JLandscapeElement::slug)
      .containsExactly(moduleSlug("athird"), moduleSlug("bsecond"), featureSlug("feat"));
  }

  @Test
  void shouldRemoveNestedDependencies() {
    Seed4JModuleResource firstModule = defaultModuleResourceBuilder().slug("first").build();
    Seed4JModuleResource secondModule = defaultModuleResourceBuilder().slug("second").moduleDependency("first").build();
    Seed4JModuleResource thirdModule = defaultModuleResourceBuilder()
      .slug("third")
      .moduleDependency("second")
      .moduleDependency("first")
      .build();
    Seed4JModuleResource forthModule = defaultModuleResourceBuilder()
      .slug("forth")
      .moduleDependency("third")
      .moduleDependency("second")
      .moduleDependency("first")
      .build();

    Seed4JLandscape landscape = Seed4JLandscape.from(moduleResources(firstModule, secondModule, thirdModule, forthModule));

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
    Seed4JModuleResource firstModule = defaultModuleResourceBuilder().slug("first").build();
    Seed4JModuleResource secondModule = defaultModuleResourceBuilder().slug("second").feature("test").moduleDependency("first").build();

    Seed4JLandscape landscape = Seed4JLandscape.from(moduleResources(firstModule, secondModule));

    assertThat(landscape.sort(List.of(moduleSlug("second"), moduleSlug("first")))).containsExactly(
      moduleSlug("first"),
      moduleSlug("second")
    );
  }

  @Test
  void shouldSortInitAsFirstModule() {
    Seed4JModuleResource init = defaultModuleResourceBuilder().slug("init").build();
    Seed4JModuleResource root = defaultModuleResourceBuilder().slug("root").build();

    Seed4JLandscape landscape = Seed4JLandscape.from(moduleResources(root, init));

    assertThat(landscape.sort(List.of(moduleSlug("root"), moduleSlug("init")))).containsExactly(moduleSlug("init"), moduleSlug("root"));
  }

  @Test
  void shouldHaveMeaningfulToString() {
    assertThat(defaultModuleResource().toString()).contains("slug=", "apiDoc=", "description=");
    assertThat(noDependencyLandscapeModule("first").toString()).contains("Seed4JLandscapeModule[module=");
  }
}
