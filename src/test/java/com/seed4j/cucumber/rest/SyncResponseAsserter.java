package com.seed4j.cucumber.rest;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.http.HttpStatus;

class SyncResponseAsserter implements ResponseAsserter {

  @Override
  public SyncResponseAsserter hasHttpStatus(HttpStatus status) {
    CucumberRestAssertions.assertHttpStatus(status);

    return this;
  }

  @Override
  public ResponseAsserter hasHttpStatusIn(HttpStatus... statuses) {
    CucumberRestAssertions.assertHttpStatusIn(statuses);

    return this;
  }

  @Override
  public SyncElementAsserter hasElement(String jsonPath) {
    return new SyncElementAsserter(this, jsonPath);
  }

  @Override
  public SyncHeaderAsserter hasHeader(String header) {
    return new SyncHeaderAsserter(this, header);
  }

  public SyncResponseAsserter doNotHaveElement(String jsonPath) {
    int elementsCount = CucumberRestTestContext.countEntries(jsonPath);

    assertThat(elementsCount).as("Expecting " + jsonPath + " to not exist " + CucumberRestAssertions.callContext()).isZero();

    return this;
  }

  @Override
  public SyncResponseAsserter hasRawBody(String info) {
    assertThat(CucumberRestAssertions.responseBody()).isEqualTo(info);

    return this;
  }
}
