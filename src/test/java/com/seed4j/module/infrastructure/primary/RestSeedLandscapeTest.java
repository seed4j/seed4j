package com.seed4j.module.infrastructure.primary;

import static com.seed4j.module.domain.resource.JHipsterModulesResourceFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.JsonHelper;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.landscape.JHipsterLandscapeFixture;
import com.seed4j.module.domain.landscape.SeedLandscape;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.junit.jupiter.api.Test;

@UnitTest
class RestSeedLandscapeTest {

  @Test
  void shouldSerializeToJson() {
    JHipsterModuleResource firstModule = defaultModuleResourceBuilder().slug("first").build();
    JHipsterModuleResource secondModule = defaultModuleResourceBuilder()
      .slug("second")
      .feature("my-feature")
      .moduleDependency("first")
      .build();

    assertThat(
      JsonHelper.writeAsString(
        RestJHipsterLandscape.from(SeedLandscape.from(JHipsterLandscapeFixture.moduleResources(firstModule, secondModule)))
      )
    ).isEqualTo(json());
  }

  private String json() {
    return """
    {\
    "levels":[\
    {"elements":[{"type":"MODULE","slug":"first","operation":"operation","properties":{PROPERTIES_DEFINITION},"rank":"RANK_D"}]},\
    {"elements":[{"type":"FEATURE","slug":"my-feature","modules":\
    [{"type":"MODULE","slug":"second","operation":"operation","properties":{PROPERTIES_DEFINITION},"dependencies":[{"type":"MODULE","slug":"first"}],"rank":"RANK_D"}]}]}\
    ]\
    }\
    """.replace("{PROPERTIES_DEFINITION}", RestSeedModulePropertiesDefinitionTest.json());
  }
}
