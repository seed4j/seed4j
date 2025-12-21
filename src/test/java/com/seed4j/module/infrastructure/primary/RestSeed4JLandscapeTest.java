package com.seed4j.module.infrastructure.primary;

import static com.seed4j.module.domain.resource.Seed4JModulesResourceFixture.defaultModuleResourceBuilder;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.JsonHelper;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.landscape.Seed4JLandscape;
import com.seed4j.module.domain.landscape.Seed4JLandscapeFixture;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.junit.jupiter.api.Test;

@UnitTest
class RestSeed4JLandscapeTest {

  @Test
  void shouldSerializeToJson() {
    Seed4JModuleResource firstModule = defaultModuleResourceBuilder().slug("first").build();
    Seed4JModuleResource secondModule = defaultModuleResourceBuilder()
      .slug("second")
      .feature("my-feature")
      .moduleDependency("first")
      .build();

    assertThat(
      JsonHelper.writeAsString(
        RestSeed4JLandscape.from(Seed4JLandscape.from(Seed4JLandscapeFixture.moduleResources(firstModule, secondModule)))
      )
    ).isEqualTo(json());
  }

  private String json() {
    // language=json
    return """
    {\
    "levels":[\
    {"elements":[{"type":"MODULE","slug":"first","operation":"operation","properties":{PROPERTIES_DEFINITION},"rank":"RANK_D"}]},\
    {"elements":[{"type":"FEATURE","slug":"my-feature","modules":[\
    {"type":"MODULE","slug":"second","operation":"operation","properties":{PROPERTIES_DEFINITION},"dependencies":[{"slug":"first","type":"MODULE"}],"rank":"RANK_D"}\
    ]}\
    ]}\
    ]\
    }\
    """.replace("{PROPERTIES_DEFINITION}", RestSeed4JModulePropertiesDefinitionTest.json());
  }
}
