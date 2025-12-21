package com.seed4j.module.infrastructure.primary;

import static com.seed4j.module.domain.resource.Seed4JModulesResourceFixture.propertiesDefinition;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.JsonHelper;
import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class RestSeed4JModulePropertiesDefinitionTest {

  @Test
  void shouldSerializeToJson() {
    assertThat(JsonHelper.writeAsString(RestSeed4JModulePropertiesDefinition.from(propertiesDefinition()))).isEqualTo(json());
  }

  static String json() {
    // language=json
    return (
      """
      {"definitions":[\
      {"defaultValue":"com.mycompany.myapp","description":"Base java package","key":"packageName","mandatory":true,"order":-300,"type":"STRING"},\
      {"defaultValue":"Seed4J Sample Application","description":"Project full name","key":"projectName","mandatory":true,"order":-200,"type":"STRING"},\
      {"defaultValue":"seed4jSampleApplication","description":"Project short name (only letters and numbers)","key":"baseName","mandatory":true,"order":-100,"type":"STRING"},\
      {"key":"mandatoryBoolean","mandatory":true,"order":0,"type":"BOOLEAN"},\
      {"key":"mandatoryInteger","mandatory":true,"order":0,"type":"INTEGER"},\
      {"key":"optionalBoolean","mandatory":false,"order":0,"type":"BOOLEAN"},\
      {"key":"optionalString","mandatory":false,"order":0,"type":"STRING"},\
      {"defaultValue":"2","description":"Number of spaces in indentation","key":"indentSize","mandatory":false,"order":500,"type":"INTEGER"}\
      ]}"""
    );
  }
}
