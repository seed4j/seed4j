package tech.jhipster.lite.merge.impl;

import tech.jhipster.lite.merge.Api;
import tech.jhipster.lite.merge.Deliver;
import tech.jhipster.lite.merge.Merge;
import tech.jhipster.lite.merge.Verify;

public class ApiImpl implements Api {

  private final Diamond context;

  final Verify.Situation verify;
  final Merge.Rs merge;

  public ApiImpl(Diamond context) {
    this.context = context;
    verify = verify();
    if (verify.mustMerge()) {
      merge = merge();
    } else {
      merge = null;
    }
  }

  Verify.Situation verify() {
    return Verify.by(context).verify();
  }

  Merge.Rs merge() {
    return Merge.by(context).merge();
  }

  /**
   * After the merge it will be natural to deliver files to correct locations.
   * Merger specify what files to operate on. Then it is the client that makes the physical operation.
   * Merge knows NOTHING about physical file. Merge abstract that in class {@link Body}
   *
   * @return next step in 'business' flow for a merge
   */
  @Override
  public DeliverImpl deliver() {
    return Deliver.by(context, verify, merge);
  }
}
