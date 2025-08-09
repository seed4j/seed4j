package com.seed4j.module.domain.javaproperties;

import com.seed4j.shared.collection.domain.SeedCollections;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public final class PropertyValue {

  private final Collection<Object> values;

  private PropertyValue(Object[] values) {
    this(List.of(values));
  }

  private PropertyValue(Collection<Object> values) {
    Assert.field("values", values).noNullElement();

    this.values = SeedCollections.immutable(values);
  }

  public static PropertyValue of(String[] values) {
    return new PropertyValue(values);
  }

  public static PropertyValue of(Boolean[] values) {
    return new PropertyValue(values);
  }

  public static PropertyValue of(Number[] values) {
    return new PropertyValue(values);
  }

  public Collection<Object> get() {
    return values();
  }

  public static PropertyValue merge(PropertyValue v1, PropertyValue v2) {
    Collection<Object> mergedValues = new ArrayList<>();
    mergedValues.addAll(v1.get());
    mergedValues.addAll(v2.get());

    return new PropertyValue(mergedValues);
  }

  public Collection<Object> values() {
    return values;
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  @SuppressWarnings("UndefinedEquals")
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    if (!(obj instanceof PropertyValue that)) {
      return false;
    }

    return Objects.equals(this.values, that.values);
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public int hashCode() {
    return Objects.hash(values);
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public String toString() {
    return "PropertyValue[" + "values=" + values + ']';
  }
}
