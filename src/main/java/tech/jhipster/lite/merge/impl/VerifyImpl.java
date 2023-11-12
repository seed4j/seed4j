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
    if (input.base().isNone() && input.custom().isNone()) {
      res = Situation.noFileBefore;
    } else if (input.base().isNone() && input.custom().exists() && !input.gen().same(input.custom())) {
      res = Situation.noGenLogButCustom;
    } else if (input.base().exists() && input.gen().same(input.base())) {
      res = Situation.generatedUnchanged;
    } else if (input.base().exists() && input.custom().exists() && input.base().same(input.custom())) {
      res = Situation.notCustomFitted;
    } else if (input.base().exists() && input.custom().exists() && !input.base().same(input.custom())) {
      res = Situation.mustMerge;
    } else {
      res = Situation.decisionFailure;
    }
    return res;
  }
}
