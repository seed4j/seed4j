package tech.jhipster.lite.merge;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.merge.impl.DeliverImpl;

@UnitTest
class ApiTest {

  /**
   * Test shows a full sample of having a previous gen-file with 'a'
   * and generate a new gen-file with value 'b'
   * and the customer has coustom fitted the file to 'c'
   * <p>
   * Outcome is a merge with error. That means that {@link DeliverImpl#whenMergeConflictCopyIncompleteMerge} is executed.
   * <p>
   * The test does not store files in filesystem.
   * The System.out.println statements must be replaced with code that store files.
   */
  @Test
  void mergeSampleWithMergeConflict() {
    //given when then
    final Deliver rs = Api
      .merge("a", "b", "c")
      .deliver()
      .copyGenToGenLog(gen -> System.out.println("please copy gen to genlog " + gen.toString()))
      .copyToSource(newSource -> System.out.println("please copy newSource to source " + newSource.toString()))
      .whenMergeConflictCopyIncompleteMerge(problematic ->
        System.out.println(
          "please copy problematic 'target' to source directory with name='name+_merged.ext' without committing to git " +
          problematic.toString()
        )
      )
      .whenPatchNeeded(
        () -> "src/main/resources/config/application.properties",
        patch ->
          System.out.println(
            "please copy 'patch' to source directory with name='name+.patch' without committing to git " + patch.toString()
          )
      )
      .end();
    Assertions.assertThat(rs.isSuccess()).isTrue();
  }
}
