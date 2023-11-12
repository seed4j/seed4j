package tech.jhipster.lite.merge.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.merge.Verify;

/**
 * Test {@link Verify} feature.
 * Spinning up situations where various files exists / are missing and 'verify' will
 * detect what {@link Verify.Situation} the {@link Diamond} is at!.
 */
@UnitTest
class VerifyTest {

  @Test
  void mergeFirstTimeExpectNoFileBefore() {
    //Given
    final Diamond diamond = Diamond.of(
      null,
      """
      gen
      """,
      null
    );
    //When
    final Verify.Situation rs = Verify.by(diamond).verify();
    //Then
    Assertions.assertThat(rs).isEqualTo(Verify.Situation.noFileBefore);
    //    Assertions.assertThat(rs.target).isEqualTo(merge.gen);
  }

  @Test
  void mergeGivenNoBaseGivenGenGivenCustomNotSameAsCustomThenNoGenLogButTarget() {
    //Given
    final Diamond diamond = Diamond.of(
      null,
      """
      gen
      """,
      """
      custom
      """
    );
    //When
    final Verify.Situation rs = Verify.by(diamond).verify();
    //Then
    Assertions.assertThat(rs).isEqualTo(Verify.Situation.noGenLogButCustom);
  }

  @Test
  void mergeGivenBaseGivenGenEqualsBaseThenGeneratedUnchanged() {
    //Given
    final Diamond diamond = Diamond.of(
      """
      unchanged
      """,
      """
      unchanged
      """,
      """
      something else
      """
    );
    //When
    final Verify.Situation rs = Verify.by(diamond).verify();
    //Then
    Assertions.assertThat(rs).isEqualTo(Verify.Situation.generatedUnchanged);
  }

  @Test
  void mergeGivenBaseGivenGenGivenGenEqualsCustomThenNotCustomFitted() {
    //Given
    final Diamond diamond = Diamond.of(
      """
      v1
      """,
      """
      v2
      """,
      """
      v1
      """
    );
    //When
    final Verify.Situation rs = Verify.by(diamond).verify();
    //Then
    Assertions.assertThat(rs).isEqualTo(Verify.Situation.notCustomFitted);
  }

  @Test
  void mergeGivenBaseGivenGenGivenGenEqualsCustomThenMustMerge() {
    //Given
    final Diamond diamond = Diamond.of(
      """
      v1
      """,
      """
      v1
      newRow
      """,
      """
      v2
      """
    );
    //When
    final Verify.Situation rs = Verify.by(diamond).verify();
    Assertions.assertThat(rs).isEqualTo(Verify.Situation.mustMerge);
  }
}
