package tech.jhipster.lite.module.domain.javaproperties;

import java.util.LinkedHashMap;
import java.util.Map;
import tech.jhipster.lite.shared.error.domain.Assert;

public class PropertiesBlockComment {

  private final Comment comment;
  private final Map<PropertyKey, PropertyValue> properties;

  private PropertiesBlockComment(PropertiesBlockCommentBuilder builder) {
    Assert.notNull("comment", builder.comment);
    Assert.notNull("properties", builder.properties);

    this.comment = builder.comment;
    this.properties = builder.properties;
  }

  public Comment comment() {
    return comment;
  }

  public Map<PropertyKey, PropertyValue> properties() {
    return properties;
  }

  public static PropertiesBlockCommentBuilder builder() {
    return new PropertiesBlockCommentBuilder();
  }

  public static class PropertiesBlockCommentBuilder
    implements PropertiesBlockCommentCommentBuilder, PropertiesBlockCommentPropertiesBuilder {

    private Comment comment;
    private final Map<PropertyKey, PropertyValue> properties = new LinkedHashMap<>();

    @Override
    public PropertiesBlockCommentPropertiesBuilder comment(Comment comment) {
      this.comment = comment;
      return this;
    }

    @Override
    public PropertiesBlockCommentPropertiesBuilder add(PropertyKey propertyKey, PropertyValue propertyValue) {
      Assert.notNull("propertyKey", propertyKey);
      Assert.notNull("propertyValue", propertyValue);

      properties.put(propertyKey, propertyValue);
      return this;
    }

    @Override
    public PropertiesBlockComment build() {
      return new PropertiesBlockComment(this);
    }
  }

  public interface PropertiesBlockCommentCommentBuilder {
    PropertiesBlockCommentPropertiesBuilder comment(Comment comment);
  }

  public interface PropertiesBlockCommentPropertiesBuilder {
    PropertiesBlockCommentPropertiesBuilder add(PropertyKey propertyKey, PropertyValue propertyValue);

    PropertiesBlockComment build();
  }
}
