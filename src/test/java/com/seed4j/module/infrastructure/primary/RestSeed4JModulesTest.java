package com.seed4j.module.infrastructure.primary;

import static com.seed4j.module.domain.resource.Seed4JModulesResourceFixture.*;
import static org.assertj.core.api.Assertions.*;

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
    return """
    {"categories":\
    [{"name":"Another group",\
    "modules":[\
    {"slug":"yet-another-module",\
    "description":"Another operation",\
    "properties":{MODULE_PROPERTIES},\
    "tags":["tag3"]}\
    ]},\
    {"name":"group","modules":[\
    {"slug":"another-module",\
    "description":"operation",\
    "properties":{MODULE_PROPERTIES},\
    "tags":["tag2"]}\
    ,{"slug":"slug","description":"operation",\
    "properties":{MODULE_PROPERTIES},\
    "tags":["tag1"]}\
    ]}]}\
    """.replace("{MODULE_PROPERTIES}", RestSeed4JModulePropertiesDefinitionTest.json());
  }
}
