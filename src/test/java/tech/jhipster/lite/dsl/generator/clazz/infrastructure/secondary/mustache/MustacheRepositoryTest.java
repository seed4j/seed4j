package tech.jhipster.lite.dsl.generator.clazz.infrastructure.secondary.mustache;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.DslClassUtils;

@UnitTest
class MustacheRepositoryTest {

  private MustacheRepository mustacheRepository;

  public MustacheRepositoryTest() {
    this.mustacheRepository = new MustacheRepository();
  }

  @Test
  void shouldGenerateClass() {
    mustacheRepository.generate(DslClassUtils.createClassToGenerate(DslClassUtils.createDefaultConfig()));
  }

  @Test
  void shouldGenerateRecord() {
    mustacheRepository.generate(DslClassUtils.createRecordToGenerate(DslClassUtils.createDefaultConfig()));
  }

  @Test
  void shouldGenerateComplexeClass() {
    mustacheRepository.generate(DslClassUtils.createClassComplexeToGenerate(DslClassUtils.createDefaultConfig()));
  }
}
