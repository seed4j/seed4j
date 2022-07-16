package tech.jhipster.lite.module.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.module.infrastructure.primary.JHipsterModulesResourceFixture.*;

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
        [{"name":"Another tag",\
        "modules":[\
        {"slug":"yet-another-module",\
        "description":"Another operation",\
        {MODULE_PROPERTIES},\
        "tags":["tag3"]}\
        ]},\
        {"name":"tag","modules":[\
        {"slug":"another-module",\
        "description":"operation",\
        {MODULE_PROPERTIES},\
        "tags":["tag2"]}\
        ,{"slug":"slug","description":"operation",\
        {MODULE_PROPERTIES},\
        "tags":["tag1"]}\
        ]}]}\
        """.replace(
        "{MODULE_PROPERTIES}",
        modulePropertiesJson()
      );
  }

  private static String modulePropertiesJson() {
    return """
        "properties":\
        {"definitions":\
        [{"type":"STRING","mandatory":true,"key":"packageName","description":"Base java package","example":"tech.jhipster.lite"},\
        {"type":"STRING","mandatory":true,"key":"projectName","description":"Project full name","example":"JHipster Project"},\
        {"type":"STRING","mandatory":true,"key":"baseName","description":"Project short name (only letters and numbers)","example":"jhipster"},\
        {"type":"BOOLEAN","mandatory":true,"key":"mandatoryBoolean"},\
        {"type":"INTEGER","mandatory":true,"key":"mandatoryInteger"},\
        {"type":"BOOLEAN","mandatory":false,"key":"optionalBoolean"},\
        {"type":"STRING","mandatory":false,"key":"optionalString"},\
        {"type":"INTEGER","mandatory":false,"key":"prettierDefaultIndent","description":"Number of spaces in indentation","example":"2"}]}\
        """;
  }
}
