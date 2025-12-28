package com.seed4j.shared.nullness.domain;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation describing a nullability post-condition for an instance method. Each parameter to
 * the annotation should be a field of the enclosing class. The method must ensure that whenever the
 * method exits normally, the fields listed in the annotation are non-null. Here is an example:
 *
 * <pre>
 * class Foo {
 *     {@literal @}Nullable Object theField;
 *     {@literal @}EnsuresNonNull("theField") // @EnsuresNonNull("this.theField") is also valid
 *     void foo() {
 *         theField = new Object();
 *     }
 *     void bar() {
 *         foo();
 *         // No error, theField is non-null after call to foo()
 *         theField.toString();
 *     }
 * }
 * </pre>
 * @see <a href="https://github.com/uber/NullAway/blob/master/annotations/src/main/java/com/uber/nullaway/annotations/EnsuresNonNull.java">com.uber.nullaway.annotations.EnsuresNonNull</a>
 * @see <a href="https://github.com/uber/NullAway/wiki/Supported-Annotations#field-contracts-precondition-and-postcondition">NullAway field contracts</a>
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.CONSTRUCTOR })
public @interface EnsuresNonNull {
  String[] value();
}
