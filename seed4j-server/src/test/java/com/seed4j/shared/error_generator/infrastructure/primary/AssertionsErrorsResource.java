package com.seed4j.shared.error_generator.infrastructure.primary;

import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.error_generator.domain.MissingMandatoryValueFactory;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assertion-errors")
class AssertionsErrorsResource {

  @GetMapping("missing-primary-mandatory-value")
  void missingPrimaryMandatoryValue() {
    Assert.notNull("myField", null);
  }

  @GetMapping("missing-domain-mandatory-value")
  void missingDomainMandatoryValue() {
    MissingMandatoryValueFactory.throwMissingMandatoryValue();
  }

  @GetMapping("number-value-too-low")
  void numberValueTooLow() {
    Assert.field("myField", 42).min(1337);
  }

  @GetMapping("number-value-too-high")
  void numberValueTooHigh() {
    Assert.field("myField", 1337).max(42);
  }

  @GetMapping("string-too-long")
  void stringTooLong() {
    Assert.field("myField", "a".repeat(42)).maxLength(10);
  }

  @GetMapping("string-too-short")
  void stringTooShort() {
    Assert.field("myField", "a".repeat(10)).minLength(42);
  }

  @GetMapping("too-many-elements")
  void tooManyElements() {
    Assert.field("myField", List.of("a", "b")).maxSize(1);
  }

  @GetMapping("null-element-in-collection")
  void nullElementInCollection() {
    Assert.field("myField", Arrays.asList("dummy", null)).noNullElement();
  }

  @GetMapping("string-with-whitespaces")
  void stringWithWhitespace() {
    Assert.field("myField", "with whitespace").noWhitespace();
  }

  @GetMapping("string-not-matching-pattern")
  void stringNotMatchingPattern() {
    Assert.field("myField", "WithSpecialCharacters?").matchesPattern(Pattern.compile("^[a-z0-9-]+$"));
  }

  @GetMapping("not-after-time")
  void notAfterTime() {
    Assert.field("myField", Instant.ofEpochSecond(13)).after(Instant.ofEpochSecond(1337));
  }

  @GetMapping("not-before-time")
  void notBeforeTime() {
    Assert.field("myField", Instant.ofEpochSecond(1337)).before(Instant.ofEpochSecond(13));
  }
}
