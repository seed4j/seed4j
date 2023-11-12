package tech.jhipster.lite.merge.impl;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class BodyPartTest {

  static final Body body = Body.of(
    """
      A
      B
      C
    """
  );

  @Test
  void givenBodyWhenSelectLinesBefore_aThenGetEmptyList() {
    //GivenWhen
    final BodyPart part = BodyPart.root(body);
    final BodyLine lineA = part.getLines().get(0);
    //Then
    Assertions.assertThat(part.beforeLine(lineA)).isEmpty();
  }

  @Test
  void givenBodyWhenSelectLinesBefore_bThenGetListWithA() {
    //GivenWhen
    final BodyPart part = BodyPart.root(body);
    final BodyLine lineA = part.getLines().get(0);
    final BodyLine lineB = part.getLines().get(1);
    //Then
    Assertions.assertThat(part.beforeLine(lineB)).hasSize(1).containsOnlyOnce(lineA);
  }

  @Test
  void givenBodyWhenSelectLinesBefore_cThenGetListWithAAndB() {
    //GivenWhen
    final BodyPart part = BodyPart.root(body);
    final BodyLine lineA = part.getLines().get(0);
    final BodyLine lineB = part.getLines().get(1);
    final BodyLine lineC = part.getLines().get(2);
    //Then
    Assertions.assertThat(part.beforeLine(lineC)).hasSize(2).containsSequence(lineA, lineB);
  }

  @Test
  void givenBodyWhenSelectLinesAfter_cThenGetEmptyList() {
    //GivenWhen
    final BodyPart part = BodyPart.root(body);
    //Then
    Assertions.assertThat(part.afterPart(part.getLines())).isEmpty();
  }

  @Test
  void givenBodyWhenSelectLinesAfter_bThenGetListWithC() {
    //GivenWhen
    final BodyPart part = BodyPart.root(body);
    final BodyLine lineB = part.getLines().get(1);
    final BodyLine lineC = part.getLines().get(2);
    //Then
    Assertions.assertThat(part.afterPart(List.of(lineB))).hasSize(1).containsOnlyOnce(lineC);
  }

  @Test
  void givenBodyWhenSelectLinesAfter_aThenGetListWithBAndC() {
    //GivenWhen
    final BodyPart part = BodyPart.root(body);
    final BodyLine lineA = part.getLines().get(0);
    final BodyLine lineB = part.getLines().get(1);
    final BodyLine lineC = part.getLines().get(2);
    //Then
    Assertions.assertThat(part.afterPart(List.of(lineA))).hasSize(2).containsSequence(lineB, lineC);
  }

  /**
   * Test check that {@link BodyPart#afterPart(List)} generates correct
   * when everything after line 13 is wanted.
   */
  @Test
  void afterPartGivenLine1to13ThenLine14and15() {
    // given
    final BodyPart part = BodyPart.root(
      Body.of(
        """
        #====================================================================
        # Standard Spring Boot properties.
        # Full reference is available at:
        # http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html
        #====================================================================

        spring.application.name=JhipsterSampleApplication

        logging.level.com.mycompany.myapp=INFO
        spring.jpa.properties.hibernate.jdbc.batch_size=25
        spring.jpa.properties.hibernate.order_updates=true
        spring.datasource.password=
        spring.jpa.hibernate.ddl-auto=none
        server.port=8080
        #
        """
      )
    );
    final BodyLine line13 = part.getLines().get(12);
    final BodyLine line14 = part.getLines().get(13);
    final BodyLine line15 = part.getLines().get(14);

    //When Then
    Assertions.assertThat(part.afterPart(List.of(line13))).hasSize(2).containsSequence(line14, line15);
  }
}
