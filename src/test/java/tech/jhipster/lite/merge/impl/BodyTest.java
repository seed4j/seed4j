package tech.jhipster.lite.merge.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class BodyTest {

  @Test
  void givenTwoLinesDocumentWhenParsingThenSeeTwoLines() {
    //Given
    String body =
      """
      dog
      cat
      """;
    //ThenThen
    Assertions.assertThat(Body.of(body).getLines()).hasSize(2);
  }

  @Test
  void givenNoInputWhenParsingThenSeeNone() {
    //GivenWhenThen
    Assertions.assertThat(Body.of(null).isMissing()).isTrue();
  }
}
