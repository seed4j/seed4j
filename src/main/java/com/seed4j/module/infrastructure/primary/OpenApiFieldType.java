package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.properties.Seed4JPropertyType;

enum OpenApiFieldType {
  STRING("string"),
  INTEGER("integer"),
  BOOLEAN("boolean");

  private final String key;

  OpenApiFieldType(String key) {
    this.key = key;
  }

  public static OpenApiFieldType from(Seed4JPropertyType propertyType) {
    return switch (propertyType) {
      case STRING -> STRING;
      case INTEGER -> INTEGER;
      case BOOLEAN -> BOOLEAN;
    };
  }

  public String key() {
    return key;
  }
}
