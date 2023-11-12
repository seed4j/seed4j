package tech.jhipster.lite.merge.impl;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ScanDoc {

  /**
   * @param left  document from yesterday
   * @param right document from today represents changes to 'left'
   */
  public static ScanDoc of(@NotNull String left, @NotNull String right) {
    return new ScanDoc(null, Body.of(left), null, Body.of(right));
  }

  final String labelLeft;
  final Body left;
  final String labelRight;
  final Body right;

  public ScanDoc(String labelLeft, Body left, String labelRight, Body right) {
    this.labelLeft = labelLeft;
    this.left = left;
    this.labelRight = labelRight;
    this.right = right;
  }

  NodeRaw open() {
    //noinspection UnnecessaryLocalVariable
    final NodeRaw root = new NodeRaw(BodyPart.root(left), BodyPart.root(right));
    return root;
  }

  /**
   * Recursive entry to drill down document
   *
   * @param raw something identified but not processed yet
   * @return parsed of above
   */
  NodeParsed parse(NodeRaw raw) {
    final NodeParsed root = NodeParsed.fromRaw(raw);
    final List<NodeParsed> details = breakDown(root);
    if (details.size() == 1) {
      return details.get(0);
    }
    root.details = details;
    return root;
  }

  NodeParsed parse() {
    return parse(open());
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("{");
    if (labelLeft != null) {
      sb.append(labelLeft).append("='").append(left).append('\'');
    } else {
      sb.append(", left=").append(left);
    }
    if (labelRight != null) {
      sb.append(", ").append(labelRight).append("='").append(right).append('\'');
    } else {
      sb.append(", right=").append(right);
    }
    sb.append('}');
    return sb.toString();
  }

  List<NodeParsed> breakDown(NodeParsed fragment) {
    final CanMatch matcher = new CanMatch();
    // Check for simple emptiness
    if (fragment.right.isMissing()) {
      if (fragment.left.isMissing()) {
        return List.of(NodeParsed.closed(fragment));
      } else {
        return List.of(NodeParsed.delete(fragment));
      }
    }

    final List<NodeParsed> details = new ArrayList<>();
    // Identify matches
    final List<NodeParsed> candidates = matcher.identifyMatches(fragment);
    if (candidates.isEmpty()) {
      if (isReplaceCandidate(fragment)) {
        details.add(NodeParsed.update(fragment, makeReplace(fragment)));
      } else {
        if (!fragment.left.lines.isEmpty()) {
          //TODO: store 2 rows before + 2 rows after
          // Intellij will free merge when row before and row after is found.
          // Intellij will ask user when the row before or row after is not found
          details.add(NodeParsed.delete(fragment));
        }
        if (!fragment.right.lines.isEmpty()) {
          //TODO: store 2 rows before + 2 rows after
          // Intellij will free merge when row before and row after is found.
          // Intellij will ask user when the row before or row after is not found
          details.add(NodeParsed.insert(fragment));
        }
      }
    } else {
      final NodeParsed bestMatch = matcher.findBestMatch(candidates);
      //      System.out.println("best:" + bestMatch);
      //Anything before best ??
      {
        final NodeRaw before = before(fragment, bestMatch);
        if (before != null) {
          details.add(parse(before));
        }
      }
      details.add(bestMatch);
      //Anything after best ??
      {
        final NodeRaw after = after(fragment, bestMatch);
        if (after != null) {
          details.add(parse(after));
        }
      }
    }
    return details;
  }

  private List<NodeParsed.Pair> makeReplace(NodeParsed fragment) {
    final List<BodyLine> src = fragment.left.lines;
    final List<BodyLine> upd = fragment.right.lines;
    List<NodeParsed.Pair> pairs = new ArrayList<>();
    //    for(BodyLine line: fragment.source.lines){
    //      int index=fragment.source.lines.
    //    }
    for (int index = 0; index < src.size(); index++) {
      final BodyLine left = src.get(index);
      final BodyLine right = upd.get(index);
      pairs.add(new NodeParsed.Pair(left, right));
    }
    return pairs;
  }

  private boolean isReplaceCandidate(NodeParsed fragment) {
    //noinspection UnnecessaryLocalVariable
    boolean res =
      !fragment.left.lines.isEmpty() && !fragment.right.lines.isEmpty() && (fragment.left.lines.size() == fragment.right.lines.size());
    return res;
  }

  private NodeRaw before(NodeParsed fragment, NodeParsed match) {
    List<BodyLine> left = fragment.left.beforeLine(match.left.first());
    List<BodyLine> right = fragment.right.beforeLine(match.right.first());
    if (left.isEmpty() && right.isEmpty()) {
      return null;
    } else {
      return new NodeRaw(BodyPart.of(fragment.left.body, left), BodyPart.of(fragment.right.body, right));
    }
  }

  private NodeRaw after(NodeParsed fragment, NodeParsed match) {
    final List<BodyLine> left = fragment.left.afterPart(match.left.getLines());
    final List<BodyLine> right = fragment.right.afterPart(match.right.getLines());
    if (left.isEmpty() && right.isEmpty()) {
      return null;
    } else {
      return new NodeRaw(BodyPart.of(fragment.left.body, left), BodyPart.of(fragment.right.body, right));
    }
  }

  static class CanMatch {

    private List<NodeParsed> identifyMatches(NodeParsed root) {
      List<NodeParsed> candidates = new ArrayList<>();
      for (BodyLine right : root.right.getLines()) {
        final List<NodeParsed.Pair> bases = root.left.matches(right);
        final List<NodeParsed> delta = candidates(root.left, bases, root.right);
        candidates.addAll(delta);
      }
      return candidates;
    }

    public List<NodeParsed> candidates(BodyPart source, List<NodeParsed.Pair> starts, BodyPart update) {
      final List<NodeParsed> nodes = new ArrayList<>();
      for (NodeParsed.Pair start : starts) {
        final List<BodyLine> tail = source.toEndOfFile(start.left());
        final List<BodyLine> keys = update.toEndOfFile(start.right());
        final NodeParsed node = candidate(source, tail, keys);
        nodes.add(node);
      }
      return nodes;
    }

    private NodeParsed candidate(BodyPart source, List<BodyLine> sourceFrom, List<BodyLine> keys) {
      if (keys.isEmpty()) throw new IllegalArgumentException("Keys cannot be empty");
      List<NodeParsed.Pair> pairs = new ArrayList<>();
      List<BodyLine> matched = new ArrayList<>();
      List<BodyLine> usedKey = new ArrayList<>();
      for (int index = 0; index < keys.size() && index < sourceFrom.size(); index++) {
        final BodyLine left = sourceFrom.get(index);
        final BodyLine right = keys.get(index);
        if (!left.identical(right)) {
          break;
        }
        matched.add(left);
        usedKey.add(right);
        pairs.add(new NodeParsed.Pair(left, right));
      }
      return NodeParsed.identical(BodyPart.of(source.body, matched), BodyPart.of(keys.get(0).body, usedKey), pairs);
    }

    /**
     * Find the best match in list of candidates.
     * 1) Must take the highest value.
     * 2) Must take the first candidate with the highest value.
     *
     * @param candidates sorted list with how many lines a NodeParsed covers.
     * @return best match
     */
    private NodeParsed findBestMatch(List<NodeParsed> candidates) {
      NodeParsed best = null;
      for (NodeParsed candidate : candidates) {
        if (best == null) {
          best = candidate;
        } else {
          if (candidate.score() > best.score()) {
            best = candidate;
          }
        }
      }
      return best;
    }
  }
}
