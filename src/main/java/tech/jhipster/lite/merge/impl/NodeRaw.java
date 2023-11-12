package tech.jhipster.lite.merge.impl;

public class NodeRaw {

  final BodyPart left;
  final BodyPart right;

  public NodeRaw(BodyPart left, BodyPart right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public String toString() {
    //noinspection StringBufferReplaceableByString
    final StringBuilder sb = new StringBuilder("{");
    sb.append("left=").append(left.lines);
    sb.append(", right=").append(right.lines);
    sb.append('}');
    return sb.toString();
  }
}
