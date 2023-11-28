package tech.jhipster.lite.merge;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import tech.jhipster.lite.ComponentTest;

@ComponentTest
class VerifyTest {

  @ParameterizedTest
  @EnumSource(Verify.Situation.class)
  void mustMerge(Verify.Situation situation) {
    if (situation == Verify.Situation.MUST_MERGE) {
      Assertions.assertThat(situation.mustMerge()).isTrue();
    } else {
      Assertions.assertThat(situation.mustMerge()).isFalse();
    }
  }

  @ParameterizedTest
  @EnumSource(Verify.Situation.class)
  void mustCopyToSource(Verify.Situation situation) {
    switch (situation) {
      case NO_FILE_BEFORE -> Assertions.assertThat(situation.mustCopyToSource()).isTrue();
      case CUSTOM_NO_BASE -> Assertions.assertThat(situation.mustCopyToSource()).isTrue();
      case UNCHANGED -> Assertions.assertThat(situation.mustCopyToSource()).isFalse();
      case NOT_CUSTOM_FITTED -> Assertions.assertThat(situation.mustCopyToSource()).isTrue();
      case MUST_MERGE -> Assertions.assertThat(situation.mustCopyToSource()).isTrue();
      case FAILED -> Assertions.assertThat(situation.mustCopyToSource()).isFalse();
    }
  }

  @ParameterizedTest
  @EnumSource(Verify.Situation.class)
  void mustCopyGenToGenLog(Verify.Situation situation) {
    switch (situation) {
      case NO_FILE_BEFORE -> Assertions.assertThat(situation.mustCopyGenToGenLog()).isTrue();
      case CUSTOM_NO_BASE -> Assertions.assertThat(situation.mustCopyGenToGenLog()).isTrue();
      case UNCHANGED -> Assertions.assertThat(situation.mustCopyGenToGenLog()).isFalse();
      case NOT_CUSTOM_FITTED -> Assertions.assertThat(situation.mustCopyGenToGenLog()).isTrue();
      case MUST_MERGE -> Assertions.assertThat(situation.mustCopyGenToGenLog()).isTrue();
      case FAILED -> Assertions.assertThat(situation.mustCopyGenToGenLog()).isFalse();
    }
  }
}
