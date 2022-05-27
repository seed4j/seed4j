package tech.jhipster.lite.cucumber;

import static org.assertj.core.api.Assertions.*;

import org.springframework.http.HttpStatus;

class SyncResponseAsserter implements ResponseAsserter {

  @Override
  public SyncResponseAsserter hasHttpStatus(HttpStatus status) {
    CucumberAssertions.assertHttpStatus(status);

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
    int elementsCount = CucumberTestContext.countEntries(jsonPath);

    assertThat(elementsCount).as("Expecting " + jsonPath + " to not exists " + CucumberAssertions.callContext()).isZero();

    return this;
  }

  @Override
  public SyncResponseAsserter hasRawBody(String info) {
    assertThat(CucumberAssertions.responseBody()).isEqualTo(info);

    return this;
  }
}
