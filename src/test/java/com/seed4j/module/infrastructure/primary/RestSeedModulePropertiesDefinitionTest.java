package com.seed4j.module.infrastructure.primary;

import static com.seed4j.module.domain.resource.SeedModulesResourceFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.JsonHelper;
import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class RestSeedModulePropertiesDefinitionTest {

  @Test
  void shouldSerializeToJson() {
    assertThat(JsonHelper.writeAsString(RestSeedModulePropertiesDefinition.from(propertiesDefinition()))).isEqualTo(json());
  }

  static String json() {
    return (
      "{\"definitions\":["
      + "{\"type\":\"STRING\",\"mandatory\":true,\"key\":\"packageName\",\"description\":\"Base java package\",\"defaultValue\":\"com.mycompany.myapp\",\"order\":-300},"
      + "{\"type\":\"STRING\",\"mandatory\":true,\"key\":\"projectName\",\"description\":\"Project full name\",\"defaultValue\":\"Seed4J Sample Application\",\"order\":-200},"
      + "{\"type\":\"STRING\",\"mandatory\":true,\"key\":\"baseName\",\"description\":\"Project short name (only letters and numbers)\",\"defaultValue\":\"seed4jSampleApplication\",\"order\":-100},"
      + "{\"type\":\"BOOLEAN\",\"mandatory\":true,\"key\":\"mandatoryBoolean\",\"order\":0},"
      + "{\"type\":\"INTEGER\",\"mandatory\":true,\"key\":\"mandatoryInteger\",\"order\":0},"
      + "{\"type\":\"BOOLEAN\",\"mandatory\":false,\"key\":\"optionalBoolean\",\"order\":0},"
      + "{\"type\":\"STRING\",\"mandatory\":false,\"key\":\"optionalString\",\"order\":0},"
      + "{\"type\":\"INTEGER\",\"mandatory\":false,\"key\":\"indentSize\",\"description\":\"Number of spaces in indentation\",\"defaultValue\":\"2\",\"order\":500}]}"
    );
  }
}
