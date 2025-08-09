package com.seed4j.module.domain.properties;

import com.seed4j.shared.collection.domain.SeedCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.Map;
import java.util.function.Predicate;

record JHipsterModuleParameters(Map<String, Object> parameters) {
  public JHipsterModuleParameters(Map<String, Object> parameters) {
    this.parameters = SeedCollections.immutable(parameters);
  }

  <T> T getOrDefault(String key, T defaultValue, Class<T> clazz) {
    return getOrDefault(key, defaultValue, clazz, t -> false);
  }

  <T> T getOrDefault(String key, T defaultValue, Class<T> clazz, Predicate<T> isEmpty) {
    Assert.notBlank("key", key);

    if (!parameters.containsKey(key)) {
      return defaultValue;
    }

    T value = get(key, clazz);

    if (isEmpty.test(value)) {
      return defaultValue;
    }

    return value;
  }

  <T> T get(String key, Class<T> clazz) {
    Assert.notBlank("key", key);

    Object property = parameters.get(key);

    if (property == null) {
      throw new UnknownPropertyException(key);
    }

    if (clazz.isInstance(property)) {
      return clazz.cast(property);
    }

    throw InvalidPropertyTypeException.builder().key(key).expectedType(clazz).actualType(property.getClass());
  }

  public Map<String, Object> get() {
    return parameters();
  }
}
