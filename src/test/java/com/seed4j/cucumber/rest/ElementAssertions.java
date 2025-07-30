package com.seed4j.cucumber.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.minidev.json.JSONArray;

class ElementAssertions {

  private final String jsonPath;

  public ElementAssertions(String jsonPath) {
    this.jsonPath = buildJsonPath(jsonPath);

    assertThat(CucumberRestTestContext.getElement(this.jsonPath)).as("Can't find " + this.jsonPath + " in response").isNotNull();
  }

  private String buildJsonPath(String jsonPath) {
    if (jsonPath == null) {
      return "$";
    }

    return jsonPath;
  }

  void withValue(Object value) {
    assertPathValue(jsonPath, value);
  }

  <Data> void containingExactly(List<Map<String, Data>> responses) {
    withElementsCount(responses.size());
    containing(responses);
  }

  <Data> void containing(Map<String, Data> response) {
    assertThat(response).as("Can't check object against a null response").isNotNull();

    response.forEach((key, value) -> assertPathValue(jsonPath + "." + CucumberJson.toCamelCase(key), value));
  }

  void withElementsCount(int count) {
    int elementsCount = CucumberRestTestContext.countEntries(jsonPath);

    assertThat(elementsCount)
      .as("Expecting " + count + " element(s) in " + jsonPath + " but got " + elementsCount + CucumberRestAssertions.callContext())
      .isEqualTo(count);
  }

  void withMoreThanElementsCount(int count) {
    int elementsCount = CucumberRestTestContext.countEntries(jsonPath);

    assertThat(elementsCount)
      .as("Expecting at least " + count + " element(s) in " + jsonPath + " but got " + elementsCount + CucumberRestAssertions.callContext())
      .isGreaterThanOrEqualTo(count);
  }

  <Data> void containing(List<Map<String, Data>> responses) {
    assertThat(responses).as("Can't check object against null responses").isNotNull();

    for (var line = 0; line < responses.size(); line++) {
      for (Map.Entry<String, Data> entry : responses.get(line).entrySet()) {
        String path = jsonPath + "[" + line + "]." + CucumberJson.toCamelCase(entry.getKey());

        assertPathValue(path, entry.getValue());
      }
    }
  }

  void withValues(Collection<String> values) {
    assertThat(values).as("Can't check object against null values").isNotNull();

    Object responseValue = CucumberRestTestContext.getElement(jsonPath);

    if (!(responseValue instanceof JSONArray)) {
      fail(jsonPath + " is not an array");
    }

    JSONArray array = (JSONArray) responseValue;
    assertThat(array)
      .as("Expecting " + jsonPath + " to contain " + values + " but got " + responseValue + CucumberRestAssertions.callContext())
      .containsExactlyElementsOf(values);
  }

  private void assertPathValue(String jsonPath, Object value) {
    Object responseValue = CucumberRestTestContext.getElement(jsonPath);

    if (responseValue != null && value instanceof String && !(responseValue instanceof String)) {
      responseValue = responseValue.toString();
    }

    assertThat(responseValue)
      .as("Expecting " + jsonPath + " to contain " + value + " but got " + responseValue + CucumberRestAssertions.callContext())
      .isEqualTo(value);
  }
}
