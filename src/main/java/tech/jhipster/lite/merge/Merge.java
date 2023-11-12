package tech.jhipster.lite.merge;

import tech.jhipster.lite.merge.impl.Body;
import tech.jhipster.lite.merge.impl.Diamond;
import tech.jhipster.lite.merge.impl.MergeImpl;

/**
 * Perform merge
 * New instance for each 'merge'
 * Precondition: {@link Diamond} contains a merge situation
 */
public interface Merge {
  static Merge by(Diamond diamond) {
    return new MergeImpl(diamond);
  }

  Rs merge();

  record Rs(MergeOutcome outcome, Body target) {
    /**
     *
     * @return this merged could not be merged with a significant trust of the automatic merge
     */
    public boolean wasMergedOkay() {
      return outcome.equals(MergeOutcome.mergedSuccessful);
    }
  }

  enum MergeOutcome {
    mergedSuccessful,
    /**
     * Automatic three-way merge cannot determinate a safe merge
     * BASE exists
     * and CUSTOM exists
     * and GEN != CUSTOM
     * and threeWayMerge cannot decide how to merge
     */
    cannotSafeMerge,
  }
}
