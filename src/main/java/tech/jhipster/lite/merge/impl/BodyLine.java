package tech.jhipster.lite.merge.impl;

import java.util.Objects;

public class BodyLine {

  public static final int NO_LINE = -1;
  final Body body;
  final int lineNo;
  final String line;

  public BodyLine(Body body, int lineNo, String line) {
    this.body = body;
    this.lineNo = lineNo;
    this.line = line;
  }

  public int getLineNo() {
    return lineNo;
  }

  public String getLine() {
    return line;
  }

  public boolean isMissing() {
    return lineNo == NO_LINE;
  }

  /**
   * Compare two lines to see if they are identical,
   * We compare "left" vs "right"
   *
   * @param other updated line
   * @return true means {@link NodeParsed.State#identical}
   */
  public boolean identical(BodyLine other) {
    return Objects.equals(line, other.line);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BodyLine bodyLine = (BodyLine) o;
    return Objects.equals(line, bodyLine.line);
  }

  @Override
  public int hashCode() {
    return Objects.hash(line);
  }

  @Override
  public String toString() {
    //noinspection StringBufferReplaceableByString
    final StringBuilder sb = new StringBuilder();
    sb.append(lineNo);
    sb.append(":").append(line);
    return sb.toString();
  }
}
