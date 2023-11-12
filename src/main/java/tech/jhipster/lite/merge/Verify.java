package tech.jhipster.lite.merge;

import tech.jhipster.lite.merge.impl.Diamond;
import tech.jhipster.lite.merge.impl.VerifyImpl;

/**
 * Prepare merging
 * <p>
 * Verify analyzes the combination of files and produces a {@link Situation}
 * <p>
 * This results in answering question; "Should we merge?".
 * This question has some detailed answers that can be found in {@link Situation}.
 */
public interface Verify {
  static VerifyImpl by(Diamond diamond) {
    return new VerifyImpl(diamond);
  }

  Situation verify();

  /**
   * Result of analyzing the
   * BASE == An existing earlier generated file taken from existing genLog tree.
   * GEN == The new generated file that is going to become the new BASE file.
   * CUSTOM == The file in "sources" tree. File might be custom modified
   */
  enum Situation {
    /**
     * First time a file is generated. Probably the first time the generator is executed
     * BASE missing and CUSTOM missing
     */
    NO_FILE_BEFORE,
    /**
     * No "base" was found, but there was a "custom" file in sources
     * And that file does not match new generated file.
     * BASE missing and CUSTOM exist and GEN != CUSTOM
     */
    CUSTOM_NO_BASE,
    /**
     * Generation of file will not change the existing gen-log file.
     * BASE exists and GEN == BASE
     */
    UNCHANGED,
    /**
     * The target custom file is not custom fitted.
     * BASE exists + GEN exists + GEN == CUSTOM
     */
    NOT_CUSTOM_FITTED,
    /**
     * Three-way merge must be performed.
     * BASE exists and BASE != CUSTOM and GEN != CUSTOM
     */
    MUST_MERGE,
    /**
     * Decision logic has failed (program logic must be improved)
     */
    FAILED;

    /**
     * @return true means that {@link Merge} must be performed
     */
    public boolean mustMerge() {
      return this == Situation.MUST_MERGE;
    }

    /**
     * Should 'gen' be copied to '.genLog' ?
     *
     * @return true when there is reason to copy. False when no reason, i.e. when file is unchanged.
     */
    public boolean mustCopyGenToGenLog() {
      return ((this != UNCHANGED) && (this == NO_FILE_BEFORE || this == CUSTOM_NO_BASE || this == NOT_CUSTOM_FITTED || this == MUST_MERGE));
    }

    public boolean mustCopyToSource() {
      return ((this != UNCHANGED) && (this == NO_FILE_BEFORE || this == CUSTOM_NO_BASE || this == NOT_CUSTOM_FITTED || this == MUST_MERGE));
    }
  }
}
