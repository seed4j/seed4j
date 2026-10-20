package com.seed4j.shared.error.infrastructure.primary;

import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import org.jspecify.annotations.Nullable;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;

@ExcludeFromGeneratedCodeCoverage(reason = "Not testing native runtime hints")
class NativeValidationHints implements RuntimeHintsRegistrar {

  @Override
  public void registerHints(RuntimeHints hints, @Nullable ClassLoader classLoader) {
    /*
    GraalVM reachability metadata registers these jboss-logging generated implementations under a typeReached condition
    which is not always satisfied in the built image, making the application fail at startup with
    "Invalid logger interface org.hibernate.validator.internal.util.logging.Log (implementation not found)".
    Register them unconditionally.
     */
    hints
      .reflection()
      .registerType(
        TypeReference.of("org.hibernate.validator.internal.util.logging.Log_$logger"),
        MemberCategory.INVOKE_DECLARED_CONSTRUCTORS
      )
      .registerType(
        TypeReference.of("org.hibernate.validator.internal.util.logging.Messages_$bundle"),
        MemberCategory.ACCESS_DECLARED_FIELDS
      );
  }
}
