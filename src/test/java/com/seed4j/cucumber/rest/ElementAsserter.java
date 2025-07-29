package com.seed4j.cucumber.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ElementAsserter<T extends ResponseAsserter> {
  ElementAsserter<T> withValue(Object value);

  <Data> ElementAsserter<T> containingExactly(List<Map<String, Data>> responses);

  <Data> ElementAsserter<T> containing(Map<String, Data> response);

  ElementAsserter<T> withElementsCount(int count);

  ElementAsserter<T> withMoreThanElementsCount(int count);

  <Data> ElementAsserter<T> containing(List<Map<String, Data>> responses);

  ElementAsserter<T> withValues(Collection<String> values);

  T and();

  default ElementAsserter<T> withValues(String... values) {
    assertThat(values).as("Can't check object against null values").isNotNull();

    return withValues(List.of(values));
  }
}
