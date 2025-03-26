package tech.jhipster.lite.module.infrastructure.primary;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.jhipster.lite.module.domain.resource.JHipsterModulesResourceFixture.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.JsonHelper;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.landscape.JHipsterLandscape;
import tech.jhipster.lite.module.domain.landscape.JHipsterLandscapeFixture;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@UnitTest
class RestJHipsterLandscapeTest {

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
        RestJHipsterLandscape.from(JHipsterLandscape.from(JHipsterLandscapeFixture.moduleResources(firstModule, secondModule)))
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
    """.replace("{PROPERTIES_DEFINITION}", RestJHipsterModulePropertiesDefinitionTest.json());
  }
}
