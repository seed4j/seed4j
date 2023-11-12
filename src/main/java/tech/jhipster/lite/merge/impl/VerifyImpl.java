package tech.jhipster.lite.merge.impl;

import jakarta.validation.constraints.NotNull;
import tech.jhipster.lite.merge.Verify;

public class VerifyImpl implements Verify {

  private final Diamond input;

  public VerifyImpl(Diamond input) {
    this.input = input;
  }

  public @NotNull Situation verify() {
    final Situation res;
    if (input.base().isMissing() && input.custom().isMissing()) {
      res = Situation.NO_FILE_BEFORE;
    } else if (input.base().isMissing() && input.custom().exists() && !input.gen().same(input.custom())) {
      res = Situation.CUSTOM_NO_BASE;
    } else if (input.base().exists() && input.gen().same(input.base())) {
      res = Situation.UNCHANGED;
    } else if (input.base().exists() && input.custom().exists() && input.base().same(input.custom())) {
      res = Situation.NOT_CUSTOM_FITTED;
    } else if (input.base().exists() && input.custom().exists() && !input.base().same(input.custom())) {
      res = Situation.MUST_MERGE;
    } else {
      res = Situation.FAILED;
    }
    return res;
  }
}
