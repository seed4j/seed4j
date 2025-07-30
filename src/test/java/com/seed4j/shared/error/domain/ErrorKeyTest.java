package com.seed4j.shared.error.domain;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

@UnitTest
class ErrorKeyTest {

  private static final String BASE_PACKAGE = "com.seed4j";

  private static final Set<Class<? extends ErrorKey>> KEYS = new Reflections(
    new ConfigurationBuilder()
      .setUrls(ClasspathHelper.forPackage(BASE_PACKAGE))
      .setScanners(Scanners.SubTypes)
      .filterInputsBy(new FilterBuilder().includePackage(BASE_PACKAGE))
  ).getSubTypesOf(ErrorKey.class);

  @Test
  void shouldHaveOnlyEnumsImplementations() {
    KEYS.forEach(error ->
      assertThat(error.isEnum() || error.isInterface())
        .as(() -> "Implementations of " + ErrorKey.class.getName() + " must be enums and " + error.getName() + " wasn't")
        .isTrue()
    );
  }

  @Test
  void shouldNotGenerateBlankStringKey() {
    KEYS.forEach(key ->
      Stream.of(key.getEnumConstants()).forEach(enumKey ->
          assertThat(enumKey.get())
            .as(() -> key + "." + enumKey + " generates a blank key")
            .isNotBlank()
        )
    );
  }
}
