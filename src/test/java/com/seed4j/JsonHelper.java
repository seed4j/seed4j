package com.seed4j;

import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

public final class JsonHelper {

  private static final ObjectMapper jsonMapper = jsonMapper();

  private JsonHelper() {}

  public static ObjectMapper jsonMapper() {
    return JsonMapper.builder()
      .changeDefaultPropertyInclusion(incl ->
        incl.withContentInclusion(JsonInclude.Include.NON_NULL).withValueInclusion(JsonInclude.Include.NON_NULL)
      )
      .disable(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY)
      .build();
  }

  public static <T> String writeAsString(T object) {
    return jsonMapper.writeValueAsString(object);
  }

  public static <T> T readFromJson(String json, Class<T> clazz) {
    try {
      return jsonMapper.readValue(json, clazz);
    } catch (JacksonException e) {
      throw new AssertionError("Error reading value from json: " + e.getMessage(), e);
    }
  }
}
