package tech.jhipster.lite.cucumber;

import java.time.Duration;
import org.assertj.core.api.SoftAssertionsProvider.ThrowingRunnable;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionFactory;

class Awaiter {

  private Awaiter() {}

  static void await(Duration maxTime, ThrowingRunnable assertion) {
    awaiter(maxTime)
      .untilAsserted(() -> {
        try {
          assertion.run();
        } catch (AssertionError e) {
          CucumberTestContext.retry();

          assertion.run();
        }
      });
  }

  private static ConditionFactory awaiter(Duration maxTime) {
    return Awaitility.await().pollDelay(Duration.ofMillis(0)).pollInterval(Duration.ofMillis(20)).atMost(maxTime);
  }
}
