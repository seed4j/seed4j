package tech.jhipster.lite.merge.impl;

import java.nio.file.Paths;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class FileContentTest {

  final String MERGE_TEST_DATA = "src/test/resources/tech/jhipster/lite/merge";

  @Test
  void test01() {
    FileContent BASE = FileContent.of(Paths.get(MERGE_TEST_DATA + "/test01/application_BASE.properties")).readAllLines();
    FileContent GEN = FileContent.of(Paths.get(MERGE_TEST_DATA + "/test01/application_GEN.properties")).readAllLines();
    FileContent CUSTOM = FileContent.of(Paths.get(MERGE_TEST_DATA + "/test01/application_CUSTOM.properties")).readAllLines();
    FileContent TARGET = FileContent.of(Paths.get(MERGE_TEST_DATA + "/test01/application_TARGET.properties")).readAllLines();

    Assertions.assertThat(BASE.isFound()).isTrue();
    Assertions.assertThat(GEN.isFound()).isTrue();
    Assertions.assertThat(CUSTOM.isFound()).isTrue();
    Assertions.assertThat(TARGET.isFound()).isTrue();
  }
}
