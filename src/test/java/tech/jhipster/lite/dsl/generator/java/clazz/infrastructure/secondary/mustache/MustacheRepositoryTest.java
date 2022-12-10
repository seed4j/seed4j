package tech.jhipster.lite.dsl.generator.java.clazz.infrastructure.secondary.mustache;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.DslClassUtils;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.GeneratorJavaRepository;
import tech.jhipster.lite.dsl.generator.java.clazz.infrastructure.secondary.TraceProjectFormatter;

@UnitTest
class MustacheRepositoryTest {

  private GeneratorJavaRepository generatorJavaRepository;

  public MustacheRepositoryTest() {
    this.generatorJavaRepository = new MustacheRepository(new TraceProjectFormatter());
  }

  @Test
  void shouldGenerateClass() {
    generatorJavaRepository.generate(DslClassUtils.createClassToGenerate(DslClassUtils.createDefaultConfig()));
  }

  @Test
  void shouldGenerateRecord() {
    generatorJavaRepository.generate(DslClassUtils.createRecordToGenerate(DslClassUtils.createDefaultConfig()));
  }

  @Test
  void shouldGenerateComplexeClass() {
    generatorJavaRepository.generate(DslClassUtils.createClassComplexeToGenerate(DslClassUtils.createDefaultConfigWithAssert()));
  }

  @Test
  void shouldGenerateComplexeRecord() {
    generatorJavaRepository.generate(DslClassUtils.createRecordComplexeToGenerate(DslClassUtils.createDefaultConfig()));
  }

  @Test
  void shouldGenerateEnumValueComplete() {
    generatorJavaRepository.generate(DslClassUtils.createEnumValueToGenerate(DslClassUtils.createDefaultConfigWithAssert()));
  }

  @Test
  void shouldGenerateEnumSimpleComplete() {
    generatorJavaRepository.generate(DslClassUtils.createEnumSimpleToGenerate(DslClassUtils.createDefaultConfigWithAssert()));
  }
}
