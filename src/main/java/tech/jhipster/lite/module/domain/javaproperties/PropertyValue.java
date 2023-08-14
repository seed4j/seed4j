package tech.jhipster.lite.module.domain.javaproperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import tech.jhipster.lite.shared.collection.domain.JHipsterCollections;
import tech.jhipster.lite.shared.error.domain.Assert;

public record PropertyValue(Collection<String> values) {
  public PropertyValue(String[] values) {
    this(List.of(values));
  }

  public PropertyValue(Collection<String> values) {
    Assert.field("values", values).noNullElement();

    this.values = JHipsterCollections.immutable(values);
  }

  public Collection<String> get() {
    return values();
  }

  public static PropertyValue merge(PropertyValue v1, PropertyValue v2) {
    Collection<String> mergedValues = new ArrayList<>();
    mergedValues.addAll(v1.get());
    mergedValues.addAll(v2.get());

    return new PropertyValue(mergedValues);
  }
}
