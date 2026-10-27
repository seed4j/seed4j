package com.seed4j.cucumber.rest;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

class AsyncElementAsserter implements ElementAsserter<AsyncResponseAsserter> {

  private final Duration maxTime;
  private final AsyncResponseAsserter responseAsserter;
  private final Supplier<ElementAssertions> assertions;

  AsyncElementAsserter(AsyncResponseAsserter responseAsserter, String jsonPath, Duration maxTime) {
    this.responseAsserter = responseAsserter;
    this.maxTime = maxTime;
    this.assertions = () -> new ElementAssertions(jsonPath);
  }

  @Override
  public AsyncElementAsserter withValues(Collection<String> values) {
    Awaiter.await(maxTime, () -> assertions.get().withValues(values));

    return this;
  }

  @Override
  public AsyncElementAsserter withElementsCount(int count) {
    Awaiter.await(maxTime, () -> assertions.get().withElementsCount(count));

    return this;
  }

  @Override
  public AsyncElementAsserter withMoreThanElementsCount(int count) {
    Awaiter.await(maxTime, () -> assertions.get().withMoreThanElementsCount(count));

    return this;
  }

  @Override
  public AsyncElementAsserter withValue(Object value) {
    Awaiter.await(maxTime, () -> assertions.get().withValue(value));

    return this;
  }

  @Override
  public <Data> AsyncElementAsserter containingExactly(List<Map<String, Data>> responses) {
    Awaiter.await(maxTime, () -> assertions.get().containingExactly(responses));

    return this;
  }

  @Override
  public <Data> AsyncElementAsserter containing(Map<String, Data> response) {
    Awaiter.await(maxTime, () -> assertions.get().containing(response));

    return this;
  }

  @Override
  public <Data> AsyncElementAsserter containing(List<Map<String, Data>> responses) {
    Awaiter.await(maxTime, () -> assertions.get().containing(responses));

    return this;
  }

  @Override
  public AsyncResponseAsserter and() {
    return responseAsserter;
  }
}
