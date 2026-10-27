package com.seed4j.cucumber.rest;

class SyncHeaderAsserter implements HeaderAsserter<SyncResponseAsserter> {

  private final SyncResponseAsserter responseAsserter;
  private final HeaderAssertions assertions;

  SyncHeaderAsserter(SyncResponseAsserter responseAsserter, String header) {
    this.responseAsserter = responseAsserter;
    assertions = new HeaderAssertions(header);
  }

  @Override
  public SyncHeaderAsserter containing(String value) {
    assertions.containing(value);

    return this;
  }

  @Override
  public SyncHeaderAsserter startingWith(String prefix) {
    assertions.startingWith(prefix);

    return this;
  }

  @Override
  public ResponseAsserter and() {
    return responseAsserter;
  }
}
