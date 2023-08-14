package tech.jhipster.lite.module.infrastructure.secondary;

import static tech.jhipster.lite.module.domain.JHipsterModule.LINE_BREAK;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import tech.jhipster.lite.module.domain.javaproperties.Comment;
import tech.jhipster.lite.module.domain.javaproperties.PropertyKey;
import tech.jhipster.lite.module.domain.javaproperties.PropertyValue;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.error.domain.GeneratorException;
import tech.jhipster.lite.shared.generation.domain.Generated;

public class PropertiesFileSpringPropertiesBlockCommentsHandler {

  private static final String HASH = "#";
  private static final String BLANK_SPACE = " ";

  private final Path file;

  public PropertiesFileSpringPropertiesBlockCommentsHandler(Path file) {
    Assert.notNull("file", file);

    this.file = file;
  }

  public void set(Comment comment, Map<PropertyKey, PropertyValue> properties) {
    Assert.notNull("comment", comment);
    Assert.notNull("properties", properties);

    updatePropertiesBlockComments(comment, properties);
  }

  @Generated
  private void updatePropertiesBlockComments(Comment comment, Map<PropertyKey, PropertyValue> properties) {
    try {
      String propertiesBlock = buildPropertiesBlockComments(comment, properties);
      if (propertiesBlock.isEmpty()) {
        return;
      }

      Files.write(file, propertiesBlock.getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error updating properties block with comment: " + e.getMessage(), e);
    }
  }

  private String buildPropertiesBlockComments(Comment comment, Map<PropertyKey, PropertyValue> properties) throws IOException {
    String currentProperties = readProperties();

    if (currentProperties.isEmpty()) {
      return currentProperties;
    }

    return organizePropertiesInBlock(currentProperties, properties, comment);
  }

  private String readProperties() throws IOException {
    if (Files.notExists(file)) {
      return "";
    }

    return Files.readString(file);
  }

  private String organizePropertiesInBlock(String currentProperties, Map<PropertyKey, PropertyValue> properties, Comment comment) {
    ExtractedProperties extractedProperties = extractPropertiesToBlock(currentProperties, properties);

    return new StringBuilder()
      .append(extractedProperties.updateProperties)
      .append(buildCommentBlock(comment))
      .append(LINE_BREAK)
      .append(extractedProperties.propertiesInBlock)
      .toString();
  }

  private ExtractedProperties extractPropertiesToBlock(String currentProperties, Map<PropertyKey, PropertyValue> properties) {
    String updateProperties = currentProperties;
    StringBuilder propertiesInBlock = new StringBuilder();
    for (PropertyKey propertyKey : properties.keySet()) {
      updateProperties = deletePreviousCommentBlock(updateProperties, propertyKey);
      PropertyAndCommentPosition propertyAndCommentPosition = findPropertyAndCommentPosition(updateProperties, propertyKey);
      organizePropertiesInBlock(propertiesInBlock, extractPropertyAndComment(updateProperties, propertyAndCommentPosition));
      updateProperties = deleteProperty(updateProperties, propertyAndCommentPosition);
    }
    return new ExtractedProperties(updateProperties, propertiesInBlock.toString());
  }

  private String buildCommentBlock(Comment comment) {
    return new StringBuilder().append(HASH).append(HASH).append(BLANK_SPACE).append(comment.get()).toString();
  }

  private String deletePreviousCommentBlock(String updateProperties, PropertyKey propertyKey) {
    PropertyPosition propertyPosition = findPropertyPosition(updateProperties, propertyKey);
    if (isFirstLine(updateProperties, propertyPosition.start)) return updateProperties;
    CommentPosition commentPosition = findPossibleCommentPosition(updateProperties, propertyPosition.start);
    if (propertyHasCommentBlock(updateProperties, commentPosition)) {
      return deleteComment(updateProperties, commentPosition);
    }
    return updateProperties;
  }

  private PropertyPosition findPropertyPosition(String updateProperties, PropertyKey propertyKey) {
    int propertyStartIndex = updateProperties.indexOf(propertyKey.get());
    int propertyLineBreakIndex = isLastLine(updateProperties, propertyStartIndex)
      ? updateProperties.length()
      : updateProperties.indexOf(LINE_BREAK, propertyStartIndex) + 1;
    return new PropertyPosition(propertyStartIndex, propertyLineBreakIndex);
  }

  private PropertyAndCommentPosition findPropertyAndCommentPosition(String updateProperties, PropertyKey propertyKey) {
    PropertyPosition propertyPosition = findPropertyPosition(updateProperties, propertyKey);
    if (isFirstLine(updateProperties, propertyPosition.start)) return new PropertyAndCommentPosition(
      propertyPosition.start,
      propertyPosition.end
    );
    CommentPosition commentPosition = findPossibleCommentPosition(updateProperties, propertyPosition.start);
    if (propertyHasComment(updateProperties, commentPosition)) {
      return new PropertyAndCommentPosition(commentPosition.start, propertyPosition.end);
    }
    return new PropertyAndCommentPosition(propertyPosition.start, propertyPosition.end);
  }

  private boolean propertyHasComment(String updateProperties, CommentPosition commentPosition) {
    String possiblePreviousComment = updateProperties.substring(commentPosition.start, commentPosition.end);
    return possiblePreviousComment.trim().startsWith(HASH);
  }

  private String extractPropertyAndComment(String updateProperties, PropertyAndCommentPosition propertyAndCommentPosition) {
    return updateProperties.substring(propertyAndCommentPosition.start, propertyAndCommentPosition.end);
  }

  private StringBuilder organizePropertiesInBlock(StringBuilder propertiesInBlock, String extractedProperty) {
    if (propertiesInBlock.isEmpty()) {
      propertiesInBlock.append(removeLastLineBreak(extractedProperty));
      return propertiesInBlock;
    }
    propertiesInBlock.append(LINE_BREAK);
    propertiesInBlock.append(removeLastLineBreak(extractedProperty));
    return propertiesInBlock;
  }

  private String deleteProperty(String updateProperties, PropertyAndCommentPosition propertyAndCommentPosition) {
    return updateProperties.substring(0, propertyAndCommentPosition.start) + updateProperties.substring(propertyAndCommentPosition.end);
  }

  private String removeLastLineBreak(String updateProperties) {
    if (updateProperties.lastIndexOf(LINE_BREAK) + 1 == updateProperties.length()) {
      return updateProperties.substring(0, updateProperties.lastIndexOf(LINE_BREAK));
    }
    return updateProperties;
  }

  private boolean isLastLine(String updateProperties, int index) {
    return updateProperties.indexOf(LINE_BREAK, index) == -1;
  }

  private boolean isFirstLine(String currentProperties, int propertyIndex) {
    return currentProperties.lastIndexOf(LINE_BREAK, propertyIndex) == -1;
  }

  private CommentPosition findPossibleCommentPosition(String updateProperties, int propertyIndex) {
    int commentLineBreakIndex = updateProperties.lastIndexOf(LINE_BREAK, propertyIndex);
    int commentStartIndex = isFirstLine(updateProperties, commentLineBreakIndex - 1)
      ? 0
      : updateProperties.lastIndexOf(LINE_BREAK, commentLineBreakIndex - 1) + 1;
    return new CommentPosition(commentStartIndex, ++commentLineBreakIndex);
  }

  private boolean propertyHasCommentBlock(String updateProperties, CommentPosition commentPosition) {
    String possiblePreviousComment = updateProperties.substring(commentPosition.start, commentPosition.end);
    return possiblePreviousComment.trim().startsWith(HASH + HASH);
  }

  private String deleteComment(String updateProperties, CommentPosition commentPosition) {
    return updateProperties.substring(0, commentPosition.start) + updateProperties.substring(commentPosition.end);
  }

  private record ExtractedProperties(String updateProperties, String propertiesInBlock) {}

  private record PropertyPosition(int start, int end) {}

  private record CommentPosition(int start, int end) {}

  private record PropertyAndCommentPosition(int start, int end) {}
}
