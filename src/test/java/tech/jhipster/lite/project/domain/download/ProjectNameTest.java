package tech.jhipster.lite.project.domain.download;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class ProjectNameTest {

  @Test
  void shouldGetFormattedFilename() {
    assertThat(new ProjectName("This is not formatted$").filename()).isEqualTo("this-is-not-formatted.zip");
  }
}
