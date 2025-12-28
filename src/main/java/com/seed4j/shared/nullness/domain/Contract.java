package com.seed4j.shared.nullness.domain;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * <p>Specifies some aspects of the method behavior depending on the arguments.
 * Can be used by tools for advanced data flow analysis. Note that this annotation
 * just describes how the code works and doesn't add any functionality by means of
 * code generation.
 *
 * <p>Inspired by {@code org.jetbrains.annotations.Contract} and {@code org.springframework.nullness.Contract} to avoid
 * requiring an extra dependency and be used in domain code, while still following the same semantics.
 *
 * @see <a href="https://github.com/JetBrains/java-annotations/blob/master/src/jvmMain/java/org/jetbrains/annotations/Contract.java">org.jetbrains.annotations.Contract</a>
 * @see <a href="https://github.com/uber/NullAway/wiki/Configuration#custom-contract-annotations">
 * NullAway custom contract annotations</a>
 */
@Documented
@Target(ElementType.METHOD)
public @interface Contract {
  /**
   * Contains the contract clauses describing causal relations between call arguments and the returned value.
   */
  String value();
}
