package tech.jhipster.lite.dsl.generator.java.clazz.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.converter.AnnotationConverter;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.converter.ClassConverter;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.converter.FieldConverter;
import tech.jhipster.lite.error.domain.Assert;

@Configuration
class DslJavaGeneratorConfiguration {

  @Bean
  AnnotationConverter javaAnnotationConverter() {
    return new AnnotationConverter();
  }

  @Bean
  FieldConverter javaFieldConverter(AnnotationConverter annotationConverter) {
    Assert.notNull("annotationConverter", annotationConverter);
    return new FieldConverter(annotationConverter);
  }

  @Bean
  ClassConverter javaClassConverter(FieldConverter fieldConverter, AnnotationConverter annotationConverter) {
    Assert.notNull("fieldConverter", fieldConverter);
    Assert.notNull("annotationConverter", annotationConverter);
    return new ClassConverter(fieldConverter, annotationConverter);
  }
}
