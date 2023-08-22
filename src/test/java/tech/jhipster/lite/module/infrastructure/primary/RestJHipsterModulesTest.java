package tech.jhipster.lite.module.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.module.domain.resource.JHipsterModulesResourceFixture.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.JsonHelper;
import tech.jhipster.lite.UnitTest;

@UnitTest
class RestJHipsterModulesTest {

  @Test
  void shouldSerializeToJson() {
    assertThat(JsonHelper.writeAsString(RestJHipsterModules.from(moduleResources()))).isEqualTo(json());
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
    """.replace("{MODULE_PROPERTIES}", RestJHipsterModulePropertiesDefinitionTest.json());
  }
}
