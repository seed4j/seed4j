package tech.jhipster.lite.module.infrastructure.secondary;

import static tech.jhipster.lite.module.domain.JHipsterModule.LINE_BREAK;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import tech.jhipster.lite.module.domain.javaproperties.Comment;
import tech.jhipster.lite.module.domain.javaproperties.PropertyKey;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.error.domain.GeneratorException;
import tech.jhipster.lite.shared.generation.domain.Generated;

class PropertiesFileSpringCommentsHandler {

  private static final String HASH = "#";
  private static final String EQUAL = "=";
  private static final String BLANK_SPACE = " ";

  private final Path file;

  public PropertiesFileSpringCommentsHandler(Path file) {
    Assert.notNull("file", file);

    this.file = file;
  }

  public void set(PropertyKey key, Comment comment) {
    Assert.notNull("key", key);
    Assert.notNull("value", comment);

    updateComments(key, comment);
  }

  @Generated
  private void updateComments(PropertyKey key, Comment comment) {
    try {
      String properties = buildComments(key, comment);
      if (properties.isEmpty()) {
        return;
      }

      Files.write(file, properties.getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error updating comments: " + e.getMessage(), e);
    }
  }

  private String buildComments(PropertyKey key, Comment comment) throws IOException {
    String currentProperties = readProperties();

    int propertyIndex = currentProperties.indexOf(propertyId(key));
    if (propertyIndex != -1) {
      currentProperties = deletePreviousComment(currentProperties, propertyIndex);
      propertyIndex = currentProperties.indexOf(propertyId(key));
      String start = currentProperties.substring(0, propertyIndex);
      String end = currentProperties.substring(propertyIndex);

      return start + commentLine(comment) + end;
    }

    return currentProperties;
  }

  private String deletePreviousComment(String currentProperties, int propertyIndex) {
    if (isFirstLine(currentProperties, propertyIndex)) return currentProperties;
    CommentPosition commentPosition = findPossibleCommentPosition(currentProperties, propertyIndex);
    if (propertyHasComment(currentProperties, commentPosition)) {
      return deleteComment(currentProperties, commentPosition);
    }
    return currentProperties;
  }

  private boolean isFirstLine(String currentProperties, int propertyIndex) {
    return currentProperties.lastIndexOf(LINE_BREAK, propertyIndex) == -1;
  }

  private CommentPosition findPossibleCommentPosition(String currentProperties, int propertyIndex) {
    int commentLineBreakIndex = currentProperties.lastIndexOf(LINE_BREAK, propertyIndex);
    int commentStartIndex = isFirstLine(currentProperties, commentLineBreakIndex - 1)
      ? 0
      : currentProperties.lastIndexOf(LINE_BREAK, commentLineBreakIndex - 1);
    return new CommentPosition(commentStartIndex, ++commentLineBreakIndex);
  }

  private boolean propertyHasComment(String currentProperties, CommentPosition commentPosition) {
    String possiblePreviousComment = currentProperties.substring(commentPosition.start, commentPosition.end);
    return possiblePreviousComment.trim().startsWith(HASH);
  }

  private String deleteComment(String currentProperties, CommentPosition commentPosition) {
    return currentProperties.substring(0, commentPosition.start) + currentProperties.substring(commentPosition.end);
  }

  private String readProperties() throws IOException {
    if (Files.notExists(file)) {
      return "";
    }

    return Files.readString(file);
  }

  private String commentLine(Comment comment) {
    return new StringBuilder().append(HASH).append(BLANK_SPACE).append(comment.get()).append(LINE_BREAK).toString();
  }

  private String propertyId(PropertyKey key) {
    return key.get() + EQUAL;
  }

  private record CommentPosition(int start, int end) {}
}
