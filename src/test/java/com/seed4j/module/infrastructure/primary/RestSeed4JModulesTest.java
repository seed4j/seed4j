package com.seed4j.module.infrastructure.primary;

import static com.seed4j.module.domain.resource.Seed4JModulesResourceFixture.moduleResources;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.JsonHelper;
import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class RestSeed4JModulesTest {

  @Test
  void shouldSerializeToJson() {
    assertThat(JsonHelper.writeAsString(RestSeed4JModules.from(moduleResources()))).isEqualTo(json());
  }

  private String json() {
    // language=json
    return """
    {"categories":\
    [{"modules":[\
    {"description":"Another operation",\
    "properties":{MODULE_PROPERTIES},\
    "slug":"yet-another-module",\
    "tags":["tag3"]}\
    ],"name":"Another group"},\
    {"modules":[\
    {"description":"operation",\
    "properties":{MODULE_PROPERTIES},\
    "slug":"another-module",\
    "tags":["tag2"]}\
    ,{"description":"operation",\
    "properties":{MODULE_PROPERTIES},\
    "slug":"slug",\
    "tags":["tag1"]}\
    ],"name":"group"}]}\
    """.replace("{MODULE_PROPERTIES}", RestSeed4JModulePropertiesDefinitionTest.json());
  }
}
