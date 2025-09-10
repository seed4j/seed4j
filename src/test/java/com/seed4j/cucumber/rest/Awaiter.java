package com.seed4j.cucumber.rest;

import java.time.Duration;
import org.assertj.core.api.SoftAssertionsProvider.ThrowingRunnable;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionFactory;

final class Awaiter {

  private Awaiter() {}

  static void await(Duration maxTime, ThrowingRunnable assertion) {
    awaiter(maxTime).untilAsserted(() -> {
      try {
        assertion.run();
      } catch (AssertionError e) {
        CucumberRestTestContext.retry();

        assertion.run();
      }
    });
  }

  private static ConditionFactory awaiter(Duration maxTime) {
    return Awaitility.await().pollDelay(Duration.ofMillis(0)).pollInterval(Duration.ofMillis(20)).atMost(maxTime);
  }
}
