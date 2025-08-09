package com.seed4j.module.infrastructure.primary;

import static com.seed4j.module.domain.resource.JHipsterModulesResourceFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.JsonHelper;
import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class RestSeedModulesTest {

  @Test
  void shouldSerializeToJson() {
    assertThat(JsonHelper.writeAsString(RestSeedModules.from(moduleResources()))).isEqualTo(json());
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
    """.replace("{MODULE_PROPERTIES}", RestSeedModulePropertiesDefinitionTest.json());
  }
}
