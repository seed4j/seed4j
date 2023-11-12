package tech.jhipster.lite.merge.impl;

import java.util.List;
import tech.jhipster.lite.merge.Merge;

public class MergeImpl implements Merge {

  final Diamond input;

  public MergeImpl(Diamond input) {
    this.input = input;
  }

  /**
   * Strategy to merge
   * 0) copy before version of "genlog" --> "base"
   * <p>
   * 1) make patches for new generated "gen" and apply to base
   * This will never make a merge conflict because generated code is kept in genlog tree.
   * "gen" file will be stored in .genlog tree.
   * (this means that genlog is NOT build from matches, but direct taken from generator)
   * <p>
   * 2) make patches for new "custom" and apply to base (the base from 1's output)
   * This can make merge conflicts.
   * When there is a conflict then we
   * a) put "new gen" --> custom
   * b) add custom merge file beside the custom file (not committed to Git)
   * When success:
   * c) double patched "base" --> custom
   */
  public Rs merge() {
    final ScanDoc scan1 = new ScanDoc("base", input.base(), "gen", input.gen());
    final List<NodeParsed> seq1 = scan1.parse().mergeSequence();

    final ScanDoc scan2 = new ScanDoc("base", input.base(), "custom", input.custom());
    final List<NodeParsed> seq2 = scan2.parse().mergeSequence();

    final BodyBuilder builder = new BodyBuilder(input.base());
    builder.apply(seq1);

    builder.apply(seq2);

    final Body target = builder.makeMergedBody();
    final MergeOutcome situation = builder.isSuccess() ? MergeOutcome.MERGED_SUCCESSFUL : MergeOutcome.CANNOT_MERGE;
    return new Rs(situation, target);
  }
}
