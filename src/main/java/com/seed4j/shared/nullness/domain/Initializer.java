package com.seed4j.shared.nullness.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to mark a method as an initializer of the {@code @NonNull} fields of a class.
 *
 * @see <a href="https://github.com/uber/NullAway/wiki/Error-Messages#initializer-method-does-not-guarantee-nonnull-field-is-initialized--nonnull-field--not-initialized">Proper initialization of @NonNull fields</a>
 */
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.METHOD })
public @interface Initializer {}
