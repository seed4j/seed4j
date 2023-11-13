package tech.jhipster.lite.merge.impl;

import java.util.function.Consumer;
import tech.jhipster.lite.merge.Deliver;
import tech.jhipster.lite.merge.Merge;
import tech.jhipster.lite.merge.Verify;

public class DeliverImpl implements Deliver {

  public DeliverImpl(Diamond diamond, Verify.Situation verify, Merge.Rs merge) {
    this.diamond = diamond;
    this.verify = verify;
    if (this.verify == null) throw new IllegalStateException("Must be verified");
    this.merge = merge;
  }

  final Diamond diamond;
  final Verify.Situation verify;
  final Merge.Rs merge;

  public DeliverImpl copyGenToGenLog(Consumer<Body> action) {
    if (verify.mustCopyGenToGenLog()) {
      action.accept(diamond.gen());
    }
    return this;
  }

  /**
   * The generated 'target' was merged correct
   *
   * @param action implement a clojure where target is stored in a file
   *               like 'src/main/resources/config/application.properties'
   *               the file is supposed to be committed.
   */
  public DeliverImpl copyToSource(Consumer<Body> action) {
    if (verify.mustCopyToSource()) {
      final Body newSource = (merge != null && merge.wasMergedOkay()) ? merge.target() : diamond.gen();
      action.accept(newSource);
    }
    return this;
  }

  /**
   * When merge could not merge without a conflict, then the idea is to
   * store the partly merged file as a 'helper' file for the user so user can
   * fix the merge problem after wards.
   * <p>
   * This method will only be called (activated) by 'merge' when a merge conflict has been detected.
   *
   * @param action clojure where you must store body in a file that is not committed to Git
   */
  public DeliverImpl whenMergeConflictCopyIncompleteMerge(Consumer<Body> action) {
    if (verify.mustCopyToSource() && (merge != null && !merge.wasMergedOkay())) {
      action.accept(merge.target());
    }
    return this;
  }

  /**
   * When the merge failed, we will place a 'patch' file beside the source file.
   * The user can use this patch file to update the source to preserve custom fittings.
   * This works fine from 'Intellij' (I have not checked another IDE)
   * Must do: generation of 'patch' is not yet implemented
   * <p>
   * This method will only be called (activated) by 'merge' when a patch is relevant.
   *
   * @param sourceFileName lambda where you must provide the name of 'source' file.
   *                       The value is needed inside 'patch' file.
   *                       Sample: 'src/main/resources/config/application.properties'
   * @param action         lambda where you must store body in a 'patch' file that is not committed to Git
   *                       In the above sample the patch file
   *                       can be called 'src/main/resources/config/application.properties.patch'
   */
  public DeliverImpl whenPatchNeeded(FileName sourceFileName, Consumer<Body> action) {
    if (verify.mustCopyToSource() && (merge != null && !merge.wasMergedOkay())) {
      final String fileName = sourceFileName.name();
      final Body patch = fileWithName(fileName);
      action.accept(patch);
    }
    return this;
  }

  private Body fileWithName(String fileName) {
    // fileName is needed in the patch file
    // put the 'fileName' into 'patch' file before
    return Body.of(String.format("this must become a patch file for %s", fileName));
  }

  @Override
  public boolean isSuccess() {
    return verify != Verify.Situation.FAILED;
  }

  @Override
  public Diamond diamond() {
    return diamond;
  }

  /**
   * Specifying of delivery lambdas is completed
   */
  public Deliver end() {
    return this;
  }
}
