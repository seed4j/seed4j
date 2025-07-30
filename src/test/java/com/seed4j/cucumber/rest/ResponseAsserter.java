package com.seed4j.cucumber.rest;

import java.util.stream.Stream;
import org.springframework.http.HttpStatus;

public interface ResponseAsserter {
  ResponseAsserter hasHttpStatus(HttpStatus status);

  ResponseAsserter hasHttpStatusIn(HttpStatus... statuses);

  ElementAsserter<?> hasElement(String jsonPath);

  HeaderAsserter<?> hasHeader(String header);

  ResponseAsserter hasRawBody(String info);

  default ElementAsserter<?> hasResponse() {
    return hasElement(null);
  }

  default ResponseAsserter hasOkStatus() {
    return hasHttpStatus(200);
  }

  default ResponseAsserter hasHttpStatus(int status) {
    return hasHttpStatus(HttpStatus.valueOf(status));
  }

  default ResponseAsserter hasHttpStatusIn(Integer... statuses) {
    return hasHttpStatusIn(Stream.of(statuses).map(HttpStatus::valueOf).toArray(HttpStatus[]::new));
  }
}
