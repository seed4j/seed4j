package tech.jhipster.lite.cucumber;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

class HeaderAssertions {

  private final String header;

  HeaderAssertions(String header) {
    this.header = header;

    assertThat(CucumberTestContext.getResponseHeader(header)).as("Can't find header " + header).isNotEmpty();
  }

  public void containing(String value) {
    List<String> values = CucumberTestContext.getResponseHeader(header);
    assertThat(values)
      .as("Expecting header " + header + " to contain " + value + " but can't find it, current values are " + displayValues(values))
      .contains(value);
  }

  public void startingWith(String prefix) {
    List<String> values = CucumberTestContext.getResponseHeader(header);

    assertThat(values)
      .as("Expecting header " + header + " to start with " + prefix + " but can't find it, current values are " + displayValues(values))
      .anyMatch(content -> content.startsWith(prefix));
  }

  private String displayValues(List<String> values) {
    return values.stream().collect(Collectors.joining(", "));
  }
}
