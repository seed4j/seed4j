package tech.jhipster.lite.cucumber;

import static org.assertj.core.api.Assertions.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.minidev.json.JSONArray;

class ElementAssertions {

  private final String jsonPath;

  public ElementAssertions(String jsonPath) {
    this.jsonPath = buildJsonPath(jsonPath);

    assertThat(CucumberTestContext.getElement(this.jsonPath)).as("Can't find " + this.jsonPath + " in response").isNotNull();
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
    assertThat(response).as("Can't check object agains a null response").isNotNull();

    response.entrySet().forEach(entry -> assertPathValue(jsonPath + "." + CucumberJson.toCamelCase(entry.getKey()), entry.getValue()));
  }

  void withElementsCount(int count) {
    int elementsCount = CucumberTestContext.countEntries(jsonPath);

    assertThat(elementsCount)
      .as("Expecting " + count + " element(s) in " + jsonPath + " but got " + elementsCount + CucumberAssertions.callContext())
      .isEqualTo(count);
  }

  void withMoreThanElementsCount(int count) {
    int elementsCount = CucumberTestContext.countEntries(jsonPath);

    assertThat(elementsCount)
      .as("Expecting at least " + count + " element(s) in " + jsonPath + " but got " + elementsCount + CucumberAssertions.callContext())
      .isGreaterThanOrEqualTo(count);
  }

  <Data> void containing(List<Map<String, Data>> responses) {
    assertThat(responses).as("Can't check object agains null responses").isNotNull();

    for (int line = 0; line < responses.size(); line++) {
      for (Map.Entry<String, Data> entry : responses.get(line).entrySet()) {
        String path = jsonPath + "[" + line + "]." + CucumberJson.toCamelCase(entry.getKey());

        assertPathValue(path, entry.getValue());
      }
    }
  }

  void withValues(Collection<String> values) {
    assertThat(values).as("Can't check object agains null values").isNotNull();

    Object responseValue = CucumberTestContext.getElement(jsonPath);

    if (!(responseValue instanceof JSONArray)) {
      fail(jsonPath + " is not an array");
    }

    JSONArray array = (JSONArray) responseValue;
    assertThat(array)
      .as("Expecting " + jsonPath + " to contain " + values + " but got " + responseValue + CucumberAssertions.callContext())
      .containsExactlyElementsOf(values);
  }

  private void assertPathValue(String jsonPath, Object value) {
    Object responseValue = CucumberTestContext.getElement(jsonPath);

    if (responseValue != null && value instanceof String && !(responseValue instanceof String)) {
      responseValue = responseValue.toString();
    }

    assertThat(responseValue)
      .as("Expecting " + jsonPath + " to contain " + value + " but got " + responseValue + CucumberAssertions.callContext())
      .isEqualTo(value);
  }
}
