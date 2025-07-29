package com.seed4j.project.domain.download;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class ProjectNameTest {

  @Test
  void shouldGetFormattedFilename() {
    assertThat(new ProjectName("This is not formatted$").filename()).isEqualTo("this-is-not-formatted.zip");
  }
}
