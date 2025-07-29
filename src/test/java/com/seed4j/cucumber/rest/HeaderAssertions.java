package com.seed4j.cucumber.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

class HeaderAssertions {

  private final String header;

  HeaderAssertions(String header) {
    this.header = header;

    assertThat(CucumberRestTestContext.getResponseHeader(header)).as("Can't find header " + header).isNotEmpty();
  }

  public void containing(String value) {
    List<String> values = CucumberRestTestContext.getResponseHeader(header);
    assertThat(values)
      .as("Expecting header " + header + " to contain " + value + " but can't find it, current values are " + displayValues(values))
      .contains(value);
  }

  public void startingWith(String prefix) {
    List<String> values = CucumberRestTestContext.getResponseHeader(header);

    assertThat(values)
      .as("Expecting header " + header + " to start with " + prefix + " but can't find it, current values are " + displayValues(values))
      .anyMatch(content -> content.startsWith(prefix));
  }

  private String displayValues(List<String> values) {
    return String.join(", ", values);
  }
}
