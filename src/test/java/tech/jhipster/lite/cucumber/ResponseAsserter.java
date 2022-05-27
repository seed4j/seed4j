package tech.jhipster.lite.cucumber;

import org.springframework.http.HttpStatus;

public interface ResponseAsserter {
  ResponseAsserter hasHttpStatus(HttpStatus status);

  ElementAsserter<?> hasElement(String jsonPath);

  HeaderAsserter<?> hasHeader(String header);

  default ElementAsserter<?> hasResponse() {
    return hasElement(null);
  }

  default ResponseAsserter hasOkStatus() {
    return hasHttpStatus(200);
  }

  default ResponseAsserter hasHttpStatus(int status) {
    return hasHttpStatus(HttpStatus.valueOf(status));
  }

  public ResponseAsserter hasRawBody(String info);
}
