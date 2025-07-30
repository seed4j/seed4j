package com.seed4j.cucumber.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import org.springframework.http.HttpStatus;

class AsyncResponseAsserter implements ResponseAsserter {

  private final Duration maxTime;

  AsyncResponseAsserter(Duration maxTime) {
    this.maxTime = maxTime;
  }

  @Override
  public AsyncResponseAsserter hasHttpStatus(HttpStatus status) {
    Awaiter.await(maxTime, () -> CucumberRestAssertions.assertHttpStatus(status));

    return this;
  }

  @Override
  public ResponseAsserter hasHttpStatusIn(HttpStatus... statuses) {
    Awaiter.await(maxTime, () -> CucumberRestAssertions.assertHttpStatusIn(statuses));

    return this;
  }

  @Override
  public AsyncElementAsserter hasElement(String jsonPath) {
    return new AsyncElementAsserter(this, jsonPath, maxTime);
  }

  @Override
  public AsyncHeaderAsserter hasHeader(String header) {
    return new AsyncHeaderAsserter(this, header, maxTime);
  }

  @Override
  public AsyncResponseAsserter hasRawBody(String info) {
    Awaiter.await(maxTime, () -> assertThat(CucumberRestAssertions.responseBody()).isEqualTo(info));

    return this;
  }
}
