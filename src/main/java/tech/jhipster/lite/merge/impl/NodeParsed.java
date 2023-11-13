package tech.jhipster.lite.merge.impl;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class NodeParsed extends NodeRaw {

  public static NodeParsed fromRaw(NodeRaw raw) {
    return new NodeParsed(raw.left, raw.right).state(State.RAW);
  }

  public static NodeParsed closed(NodeParsed fragment) {
    return new NodeParsed(fragment.left, fragment.right).state(State.CLOSED);
  }

  public static NodeParsed identical(BodyPart left, BodyPart right, List<Pair> pairs) {
    return new NodeParsed(left, right).pairs(pairs).state(NodeParsed.State.IDENTICAL);
  }

  public static NodeParsed delete(NodeParsed fragment) {
    return new NodeParsed(fragment.left, fragment.right).state(State.DELETE);
  }

  public static NodeParsed insert(NodeParsed fragment) {
    return new NodeParsed(fragment.left, fragment.right).state(State.INSERT);
  }

  public static NodeParsed update(NodeParsed fragment, List<Pair> pairs) {
    return new NodeParsed(fragment.left, fragment.right).pairs(pairs).state(State.UPDATE);
  }

  State state;

  final List<Pair> pairs = new ArrayList<>();
  List<NodeParsed> details = new ArrayList<>();
  /**
   * The node that came before this node
   * will be described better.
   */
  NodeParsed previous;

  public NodeParsed(BodyPart left, BodyPart right) {
    super(left, right);
  }

  public List<NodeParsed> getDetails() {
    return details;
  }

  private NodeParsed state(State state) {
    this.state = state;
    return this;
  }

  public NodeParsed pairs(List<Pair> pairs) {
    this.pairs.addAll(pairs);
    return this;
  }

  /**
   * Candidate score.
   *
   * @return value for how much this candidate covers in body.
   */
  public int score() {
    return right.lines.size();
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("{");
    sb.append("state=").append(state);
    final String sizeLabel = ", size=";
    if (state == State.INSERT) {
      sb.append(sizeLabel).append(right.lines.size());
      sb.append(", right=").append(right.lines);
    } else if (state == State.DELETE) {
      sb.append(sizeLabel).append(left.lines.size());
      sb.append(", left=").append(left.lines);
    } else if (state == State.UPDATE) {
      sb.append(", pairs=").append(pairs);
    } else if (state == State.IDENTICAL) {
      sb.append(", score=").append(score());
      sb.append(sizeLabel).append(pairs.size());
      if (pairs.size() < 3) sb.append(", pairs=").append(pairs);
    } else {
      if (!left.lines.isEmpty()) {
        if (left.lines.size() < 3) {
          sb.append(", left=").append(left.lines);
        } else {
          sb.append(", left.size=").append(left.lines.size());
        }
      }
      if (!right.lines.isEmpty()) {
        if (right.lines.size() < 3) {
          sb.append(", right=").append(right.lines);
        } else {
          sb.append(", right.size=").append(right.lines.size());
        }
      }
      if (!details.isEmpty()) sb.append(", details.size=").append(details.size());
    }
    sb.append('}');
    return sb.toString();
  }

  public List<NodeParsed> mergeSequence() {
    final List<NodeParsed> list = new ArrayList<>(Math.max(left.size(), right.size()));
    final AtomicReference<NodeParsed> previousHolder = new AtomicReference<>();
    this.addNode2list(list, previousHolder);
    return list;
  }

  void addNode2list(List<NodeParsed> list, AtomicReference<NodeParsed> previous) {
    if (state != State.RAW) {
      this.previous = previous.get();
      list.add(this);
      previous.set(this);
    }
    for (NodeParsed detail : getDetails()) {
      detail.addNode2list(list, previous);
    }
  }

  /**
   * Two lines one from left and one from right that is candidate to be same line
   *
   * @param left
   * @param right
   */
  public record Pair(@NotNull BodyLine left, @NotNull BodyLine right) {}

  public enum State {
    /**
     * A fragment that has not been analyzed yet.
     */
    RAW,
    CLOSED,
    /**
     * The section is identical in both bodies. Can be "copied"
     * Line content must be taken from body "update". Because it can be formatted the best.
     */
    IDENTICAL,
    /**
     * Delete lines from body 'left'
     */
    DELETE,
    /**
     * Insert lines from body 'right'
     */
    INSERT,
    /**
     * Rows in fragment must be updated
     */
    UPDATE,
  }
}
