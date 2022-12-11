package tech.jhipster.lite.dsl.generator.java.clazz.infrastructure.secondary.mustache;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.DslClassUtils;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.ClassToGenerate;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.EnumToGenerate;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.GeneratorJavaRepository;
import tech.jhipster.lite.dsl.generator.java.clazz.infrastructure.secondary.TraceProjectFormatter;

@UnitTest
class MustacheRepositoryTest {

  private GeneratorJavaRepository generatorJavaRepository;

  @Mock
  private GeneratorJavaRepository mockGenerator;

  public MustacheRepositoryTest() {
    this.generatorJavaRepository = new MustacheRepository(new TraceProjectFormatter());
  }

  @Test
  void shouldGenerateClass() {
    ClassToGenerate classToGenerate = DslClassUtils.createClassToGenerate(DslClassUtils.createDefaultConfig());
    generatorJavaRepository.generate(classToGenerate);
    assertTrue(Files.exists(classToGenerate.getPathFile()));
  }

  @Test
  void shouldGenerateRecord() {
    ClassToGenerate classToGenerate = DslClassUtils.createRecordToGenerate(DslClassUtils.createDefaultConfig());
    generatorJavaRepository.generate(classToGenerate);
    assertTrue(Files.exists(classToGenerate.getPathFile()));
  }

  @Test
  void shouldGenerateComplexeClass() {
    ClassToGenerate classToGenerate = DslClassUtils.createClassComplexeToGenerate(DslClassUtils.createDefaultConfigWithAssert());
    generatorJavaRepository.generate(classToGenerate);
    assertTrue(Files.exists(classToGenerate.getPathFile()));
  }

  @Test
  void shouldGenerateComplexeRecord() {
    ClassToGenerate classToGenerate = DslClassUtils.createRecordComplexeToGenerate(DslClassUtils.createDefaultConfig());
    generatorJavaRepository.generate(classToGenerate);
    assertTrue(Files.exists(classToGenerate.getPathFile()));
  }

  @Test
  void shouldGenerateEnumValueComplete() {
    EnumToGenerate enumToGenerate = DslClassUtils.createEnumValueToGenerate(DslClassUtils.createDefaultConfigWithAssert());
    generatorJavaRepository.generate(enumToGenerate);
    assertTrue(Files.exists(enumToGenerate.getPathFile()));
  }

  @Test
  void shouldGenerateEnumSimpleComplete() {
    EnumToGenerate enumToGenerate = DslClassUtils.createEnumSimpleToGenerate(DslClassUtils.createDefaultConfigWithAssert());
    generatorJavaRepository.generate(enumToGenerate);
    assertTrue(Files.exists(enumToGenerate.getPathFile()));
  }
}
