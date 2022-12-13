package tech.jhipster.lite.dsl.generator.java.clazz.domain.field;

import java.util.Optional;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;
import tech.jhipster.lite.error.domain.Assert;

public record FieldTypeImpl(String name, Optional<ClassImport> import_) implements FieldType {
  public static FieldTypeImpl fieldLong = new FieldTypeImpl("Long", Optional.empty());
  public static FieldTypeImpl fieldInteger = new FieldTypeImpl("Integer", Optional.empty());
  public static FieldTypeImpl fieldBigDecimal = new FieldTypeImpl(
    "BigDecimal",
    Optional.of(new ClassImport("java.math.BigDecimal", false))
  );
  public static FieldTypeImpl fieldFloat = new FieldTypeImpl("Float", Optional.empty());
  public static FieldTypeImpl fieldDouble = new FieldTypeImpl("Double", Optional.empty());
  public static FieldTypeImpl fieldString = new FieldTypeImpl("String", Optional.empty());
  public static FieldTypeImpl fieldBlob = new FieldTypeImpl("Blob", Optional.of(new ClassImport("java.sql.Blob", false)));
  public static FieldTypeImpl fieldInstant = new FieldTypeImpl("Instant", Optional.of(new ClassImport("java.time.Instant", false)));
  public static FieldTypeImpl fieldLocalDate = new FieldTypeImpl("LocalDate", Optional.of(new ClassImport("java.time.LocalDate", false)));
  public static FieldTypeImpl fieldZonedDateTime = new FieldTypeImpl(
    "ZonedDateTime",
    Optional.of(new ClassImport("java.time.ZonedDateTime", false))
  );
  public static FieldTypeImpl fieldDuration = new FieldTypeImpl("Duration", Optional.of(new ClassImport("java.time.Duration", false)));
  public static FieldTypeImpl fieldPeriod = new FieldTypeImpl("Period", Optional.of(new ClassImport("java.time.Period", false)));
  public static FieldTypeImpl fieldBoolean = new FieldTypeImpl("Boolean", Optional.empty());

  public FieldTypeImpl {
    Assert.field("key", name).noWhitespace();
    Assert.notNull("import_", import_);
  }

  @Override
  public Optional<ClassImport> getImport() {
    return import_();
  }
}
