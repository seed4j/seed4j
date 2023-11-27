package tech.jhipster.lite.merge.impl;

import java.util.ArrayList;
import java.util.List;

public class BodyBuilder {

  final List<UpLine> lines = new ArrayList<>();

  final List<NodeParsed.Pair> sourceUpdates = new ArrayList<>();

  StringBuilder problems = new StringBuilder();

  public BodyBuilder(Body body) {
    // the first line is a NO_LINE to grab when adding before first line
    add(new BodyLine(body, BodyLine.NO_LINE, ""));
    for (BodyLine line : body.getLines()) {
      add(line);
    }
  }

  private void add(BodyLine line) {
    final UpLine upLine = new UpLine(line);
    if (line.isMissing()) {
      upLine.removeLine();
    }
    lines.add(upLine);
  }

  public UpLine findBy(BodyLine sLine) {
    for (UpLine ul : lines) {
      if (sLine.lineNo == ul.line.lineNo) return ul;
    }
    throw new IllegalArgumentException("body line not found :" + sLine);
  }

  boolean isSuccess() {
    return problems.isEmpty();
  }

  @Override
  public String toString() {
    //noinspection StringBufferReplaceableByString
    final StringBuilder sb = new StringBuilder("{");
    sb.append("lines=").append(lines);
    sb.append('}');
    return sb.toString();
  }

  public void apply(List<NodeParsed> fragments) {
    for (NodeParsed fragment : fragments) {
      if (fragment.state != NodeParsed.State.IDENTICAL) {
        applyNonIdentical(fragment);
      }
    }
  }

  private void applyNonIdentical(NodeParsed fragment) {
    switch (fragment.state) {
      case DELETE -> {
        for (BodyLine line : fragment.left.lines) {
          final UpLine uLine = findBy(line);
          uLine.removeLine();
        }
      }
      case INSERT -> {
        if (fragment.right.lines.isEmpty()) throw new IllegalStateException("Insert must contain rows");
        final BodyLine left = findLeft(fragment);
        final UpLine uLine0 = findBy(left);
        final int index = lines.indexOf(uLine0);
        final UpLine uLine = new UpLine(left).removeLine();
        if (index + 1 == lines.size()) {
          lines.add(uLine);
        } else {
          lines.add(index + 1, uLine);
        }
        uLine.inserts.addAll(fragment.right.lines);
      }
      case UPDATE -> {
        final List<NodeParsed.Pair> pairs = fragment.pairs;
        checkConflictOk(pairs);
        if (pairs.isEmpty()) throw new IllegalStateException("Update must contain pairs");
        for (NodeParsed.Pair pair : pairs) {
          final UpLine left = findBy(pair.left());
          left.removeLine();
          left.inserts.add(pair.right());
        }
      }
      default -> throw new IllegalArgumentException("fragments contais other: " + fragment.state);
    }
  }

  @SuppressWarnings("UnusedReturnValue")
  private boolean checkConflictOk(List<NodeParsed.Pair> pairs) {
    boolean ok = true;
    for (NodeParsed.Pair pair : pairs) {
      final NodeParsed.Pair existing = findByLeft(pair.left());
      if (existing != null) {
        problems.append(
          String.format(
            "Line %s with value '%s' is updated both from 'gen' with '%s' and 'custom' with '%s'%n",
            existing.left().getLineNo(),
            existing.left().getLine(),
            existing.right().getLine(),
            pair.right().getLine()
          )
        );
        ok = false;
      } else {
        sourceUpdates.add(pair);
      }
    }
    return ok;
  }

  private NodeParsed.Pair findByLeft(BodyLine left) {
    for (NodeParsed.Pair pair : sourceUpdates) {
      if (pair.left().getLineNo() == left.getLineNo()) return pair;
    }
    return null;
  }

  /**
   * Loop through previous fragments to find where to insert update.
   *
   * @param fragment go back from this fragment
   * @return {@link BodyLine} where merge should insert "update" rows.
   */
  private BodyLine findLeft(NodeParsed fragment) {
    BodyLine res = lines.get(0).line;
    boolean found = false;
    final NodeParsed prev = fragment.previous;
    while (prev != null && !found) {
      if (!prev.right.isMissing() && !prev.left.isMissing()) {
        res = prev.left.last();
        found = true;
      }
    }
    return res;
  }

  /**
   * Output from diamond merge.
   * This is the diamond right side that is produced from 'base' + patches from 'gen' and patches from 'custom'
   * @return target
   */
  public Body makeMergedBody() {
    List<String> li = new ArrayList<>();
    for (UpLine u : lines) {
      if (!u.removed) {
        li.add(u.line.line);
      }
      for (BodyLine bl : u.inserts) {
        li.add(bl.line);
      }
    }
    return new Body(li);
  }

  public static class UpLine {

    final BodyLine line;
    boolean removed;

    final List<BodyLine> inserts = new ArrayList<>();

    public UpLine(BodyLine line) {
      this.line = line;
    }

    public UpLine removeLine() {
      this.removed = true;
      return this;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("{");
      sb.append("line=").append(line);
      if (removed) sb.append(", removed=").append(removed);
      if (!inserts.isEmpty()) sb.append(", inserts=").append(inserts);
      sb.append('}');
      return sb.toString();
    }
  }
}
