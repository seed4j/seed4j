package tech.jhipster.lite.merge.impl;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * A body part is a fragment of lines in a body.
 * Under the processing of merge then application produces several parts to store temporary data.
 */
public class BodyPart {

  public static BodyPart root(Body body) {
    return new BodyPart(body, body.getLines());
  }

  public static BodyPart empty(Body body) {
    return new BodyPart(body, List.of());
  }

  public static BodyPart of(Body body, List<BodyLine> lines) {
    return new BodyPart(body, lines);
  }

  final Body body;

  final List<BodyLine> lines;

  public BodyPart(Body body, List<BodyLine> lines) {
    this.body = body;
    this.lines = lines;
  }

  /**
   * The file body that this fragment of lines belongs to
   *
   * @return reference to the source as read from "disk" / "generated"
   */
  public @NotNull Body getBody() {
    return body;
  }

  /**
   *
   */
  public List<BodyLine> getLines() {
    return lines;
  }

  /**
   * Select the lines before this line.
   * The objective is to be able to determine if a merge is complete or need more merge
   *
   * @param line to get lines before (this line will not be included). Null gives empty result
   * @return list of lines before given line
   */
  public List<BodyLine> beforeLine(@Nullable BodyLine line) {
    if (line == null) return List.of();
    final int toIndex = indexOf(line);
    return lines.subList(0, toIndex);
  }

  /**
   * Select the lines in this {@link BodyPart} after another "query" {@link BodyPart}
   * The objective is to be able to determine if a merge is complete or need more merge
   *
   * @param query to get lines after. Empty gives an empty result
   * @return list of lines after
   */
  public List<BodyLine> afterPart(@NotNull List<BodyLine> query) {
    if (query.isEmpty()) return List.of();
    final BodyLine last = query.get(query.size() - 1);
    int index = lines.indexOf(last);
    if (index == -1 || index + 1 == lines.size()) return List.of();
    return lines.subList(index + 1, lines.size());
  }

  /**
   * Select the lines 'from' to end of file
   *
   * @param from line including
   * @return list from line and rest of selection until the end.
   */
  public List<BodyLine> toEndOfFile(BodyLine from) {
    final int fromIndex = indexOf(from);
    final int toIndex = lines.size();
    return lines.subList(fromIndex, toIndex);
  }

  private int indexOf(BodyLine from) {
    int index = 0;
    for (BodyLine one : lines) {
      if (one.lineNo == from.lineNo) return index;
      index++;
    }
    throw new IllegalArgumentException(String.format("from=%s not found in list=%s", from, lines));
  }

  public BodyLine last() {
    return lines.get(lines.size() - 1);
  }

  public boolean isMissing() {
    return lines.isEmpty();
  }

  public List<NodeParsed.Pair> matches(@NotNull BodyLine right) {
    return lines.stream().filter(left -> left.equals(right)).map(left -> new NodeParsed.Pair(left, right)).toList();
  }

  public int size() {
    return lines.size();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BodyPart bodyPart = (BodyPart) o;
    return Objects.equals(lines, bodyPart.lines);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lines);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    if (lines.size() < 3) {
      sb.append(lines);
    } else {
      sb.append("size=").append(lines.size());
    }
    return sb.toString();
  }

  public BodyLine first() {
    return lines.isEmpty() ? null : lines.get(0);
  }
}
