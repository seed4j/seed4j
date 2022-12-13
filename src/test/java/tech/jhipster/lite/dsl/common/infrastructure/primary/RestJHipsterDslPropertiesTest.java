package tech.jhipster.lite.dsl.common.infrastructure.primary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.projectfolder.domain.ForcedProjectFolder;

@UnitTest
class RestJHipsterDslPropertiesTest {

  @Test
  void mustCreateFieldWithModifier() {
    RestJHipsterDslProperties prop = new RestJHipsterDslProperties("tot", false);
    ForcedProjectFolder pro = new ForcedProjectFolder("/sqs");
    assertThrows(
      InvalidProjectFolderException.class,
      () -> {
        prop.toDomain(pro);
      },
      "Type not defined"
    );
  }
}
