package com.seed4j.cucumber.rest;

import java.util.Collection;
import java.util.List;
import java.util.Map;

class SyncElementAsserter implements ElementAsserter<SyncResponseAsserter> {

  private final SyncResponseAsserter responseAsserter;
  private final ElementAssertions assertions;

  SyncElementAsserter(SyncResponseAsserter responseAsserter, String jsonPath) {
    this.responseAsserter = responseAsserter;
    assertions = new ElementAssertions(jsonPath);
  }

  @Override
  public SyncElementAsserter withValue(Object value) {
    assertions.withValue(value);

    return this;
  }

  @Override
  public <Data> SyncElementAsserter containingExactly(List<Map<String, Data>> responses) {
    assertions.containingExactly(responses);

    return this;
  }

  @Override
  public <Data> SyncElementAsserter containing(Map<String, Data> response) {
    assertions.containing(response);

    return this;
  }

  @Override
  public SyncElementAsserter withElementsCount(int count) {
    assertions.withElementsCount(count);

    return this;
  }

  @Override
  public SyncElementAsserter withMoreThanElementsCount(int count) {
    assertions.withMoreThanElementsCount(count);

    return this;
  }

  @Override
  public <Data> SyncElementAsserter containing(List<Map<String, Data>> responses) {
    assertions.containing(responses);

    return this;
  }

  @Override
  public SyncElementAsserter withValues(Collection<String> values) {
    assertions.withValues(values);

    return this;
  }

  @Override
  public SyncResponseAsserter and() {
    return responseAsserter;
  }
}
