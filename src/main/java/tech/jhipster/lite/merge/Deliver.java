package tech.jhipster.lite.merge;

import tech.jhipster.lite.merge.impl.DeliverImpl;
import tech.jhipster.lite.merge.impl.Diamond;

/**
 * Deliver is about writing files to correct places dependent on merge result.
 * Here process will store file in file system dependent on the {@link Verify.Situation}
 * <p>
 * Merge provides lambda call back closures that client must implement.
 * <p>
 * There are four things to do:
 * <ol>
 *   <li>Copy file to .genLog. That will be the newly generated file</li>
 *   <li>Copy file to 'source'. That will in best case be the merged result {@link Role#TARGET}.
 *   In case of problems it will be the {@link Role#GEN} file that overwrites 'source'</li>
 *   <li>When merge conflicts we copy the partly {@link Role#TARGET} as a sample beside the 'source'</li>
 *   <li>And we produce a 'patch' file beside 'source'</li>
 * </ol>
 */
public interface Deliver {
  static DeliverImpl by(Diamond diamond, Verify.Situation verify, Merge.Rs merge) {
    return new DeliverImpl(diamond, verify, merge);
  }

  /**
   * @return true when merge technical was a success. Might still contain merge conflicts.
   */
  boolean isSuccess();

  /**
   * @return the input to three-way merge
   */
  Diamond diamond();

  /**
   * Client must fill in filename when 'patch' is produced
   */
  interface FileName {
    /**
     * @return the path from project root. Sample value: "src/main/resources/config/application.properties"
     */
    String name();
  }
}
