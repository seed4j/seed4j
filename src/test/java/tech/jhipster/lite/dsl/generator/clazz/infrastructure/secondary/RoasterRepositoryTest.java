package tech.jhipster.lite.dsl.generator.clazz.infrastructure.secondary;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.DslClassUtils;
import tech.jhipster.lite.dsl.generator.clazz.domain.ClassToGenerate;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;

@UnitTest
class RoasterRepositoryTest {

  private RoasterRepository roasterRepository;

  public RoasterRepositoryTest() {
    this.roasterRepository = new RoasterRepository();
  }

  @Test
  void shouldGenerateClass() {
    roasterRepository.generate(DslClassUtils.createClassToGenerate());
  }
}
