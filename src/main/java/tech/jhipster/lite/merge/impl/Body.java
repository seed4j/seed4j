package tech.jhipster.lite.merge.impl;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import tech.jhipster.lite.merge.Role;

/**
 * Content of a file.
 * A merge will make around 4 instances of this class: one for each {@link Role}.
 * <p>
 * {@link Body} is an abstraction of file content.
 * It is up to the client to fill data into 'body'.ies.
 */
public class Body {

  /**
   * Produce a body from a text stream that represents a file.
   *
   * @param content multi line string (or null)
   * @return an instance with the body lines or the NONE {@link #NONE}
   */
  public static @NotNull Body of(@Nullable String content) {
    if (content == null || content.isEmpty()) return Body.NONE;
    return new Body(toList(content));
  }

  public static List<String> toList(String content) {
    return Arrays.asList(content.split("\\r?\\n"));
  }

  public static final Body NONE = new Body();
  private final BodyPart part;
  private final boolean missing;

  private Body() {
    this.part = BodyPart.empty(this);
    this.missing = true;
  }

  public Body(@NotNull List<String> lines) {
    final AtomicInteger lineNo = new AtomicInteger();
    final List<BodyLine> bl = lines.stream().map(s -> new BodyLine(this, lineNo.incrementAndGet(), s)).toList();
    this.part = BodyPart.of(this, bl);
    this.missing = false;
  }

  public List<BodyLine> getLines() {
    return part.getLines();
  }

  /**
   * None is an isolated concept of having a body that not exists.
   *
   * @return true when body does not exist.
   */
  public boolean isMissing() {
    return missing;
  }

  public boolean exists() {
    return !missing;
  }

  public int size() {
    return part.size();
  }

  public boolean same(Body body) {
    return this.equals(body);
  }

  public boolean isEmpty() {
    return getLines().isEmpty();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Body body = (Body) o;
    return missing == body.missing && Objects.equals(part, body.part);
  }

  @SuppressWarnings("unused")
  public void print(PrintStream out) {
    for (BodyLine line : getLines()) {
      out.println(line.getLine());
    }
  }

  @SuppressWarnings("unused")
  public String asString() {
    final StringBuilder sb = new StringBuilder();
    for (BodyLine line : getLines()) {
      if (!sb.isEmpty()) sb.append("\n");
      sb.append(line.getLine());
    }
    return sb.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(part);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    if (missing) {
      sb.append("none");
    } else {
      sb.append("lines=").append(part.lines.size());
    }
    return sb.toString();
  }
}
