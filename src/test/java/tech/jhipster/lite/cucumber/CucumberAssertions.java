package tech.jhipster.lite.cucumber;

import static org.assertj.core.api.Assertions.*;

import java.time.Duration;
import org.assertj.core.description.Description;
import org.springframework.http.HttpStatus;

public final class CucumberAssertions {

  private static final CallDescription JSON_DESCRIPTION = new CallDescription();

  private CucumberAssertions() {}

  public static ResponseAsserter assertThatLastResponse() {
    return new SyncResponseAsserter();
  }

  public static ResponseAsserter assertThatLastAsyncResponse() {
    return assertThatLastAsyncResponse(Duration.ofSeconds(4));
  }

  public static ResponseAsserter assertThatLastAsyncResponse(Duration maxDelay) {
    assertThat(maxDelay).as("Can't check async responses without maxDelay").isNotNull();

    return new AsyncResponseAsserter(maxDelay);
  }

  static void assertHttpStatus(HttpStatus status) {
    assertThat(CucumberTestContext.getStatus())
      .as("Expecting request to result in " + status + " but got " + CucumberTestContext.getStatus() + callContext())
      .isEqualTo(status);
  }

  static Description callContext() {
    return JSON_DESCRIPTION;
  }

  static String responseBody() {
    return CucumberTestContext.getResponse().get();
  }

  private static class CallDescription extends Description {

    @Override
    public String value() {
      return (
        " from " +
        CucumberTestContext.getUri() +
        " in " +
        System.lineSeparator() +
        CucumberTestContext.getResponse().map(CucumberJson::pretty).orElse("empty")
      );
    }
  }
}
