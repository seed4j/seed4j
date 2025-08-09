package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.SeedModule.LINE_BREAK;

import com.seed4j.module.domain.javaproperties.Comment;
import com.seed4j.module.domain.javaproperties.PropertyKey;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.error.domain.GeneratorException;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

class PropertiesFileSpringCommentsHandler {

  private static final String HASH = "#";
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

  @ExcludeFromGeneratedCodeCoverage(reason = "Hard to cover IOException")
  private void updateComments(PropertyKey key, Comment comment) {
    try {
      String properties = buildComments(key, comment);
      if (properties.isEmpty()) {
        return;
      }

      Files.writeString(file, properties);
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error updating comments: " + e.getMessage(), e);
    }
  }

  private String buildComments(PropertyKey key, Comment comment) throws IOException {
    String currentProperties = readProperties();

    int propertyIndex = currentProperties.indexOf(key.get());
    if (propertyIndex != -1) {
      currentProperties = deletePreviousComment(currentProperties, propertyIndex);
      propertyIndex = currentProperties.indexOf(key.get());
      String start = currentProperties.substring(0, propertyIndex);
      String end = currentProperties.substring(propertyIndex);

      return start + commentLine(comment) + end;
    }

    return currentProperties;
  }

  private String deletePreviousComment(String currentProperties, int propertyIndex) {
    if (isFirstLine(currentProperties, propertyIndex)) {
      return currentProperties;
    }
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
    StringBuilder stringBuilder = new StringBuilder();
    splitLines(comment).forEach(line -> stringBuilder.append(HASH).append(BLANK_SPACE).append(line).append(LINE_BREAK));
    return stringBuilder.toString();
  }

  private static Collection<String> splitLines(Comment comment) {
    return List.of(comment.get().split("\\r?\\n"));
  }

  private record CommentPosition(int start, int end) {}
}
