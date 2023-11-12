package tech.jhipster.lite.merge.impl;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class ScanDocTest {

  @Test
  void givenSourceGivenEmptyTargetThenStateDelete() {
    //Given
    ScanDoc factory = ScanDoc.of(
      """
      spring.jpa.properties.hibernate.generate_statistics=false
      spring.jpa.properties.hibernate.jdbc.batch_size=25
      spring.jpa.properties.hibernate.order_updates=true
      spring.datasource.password=
      spring.jpa.hibernate.ddl-auto=none
      """,
      """
      """
    );
    //When
    final NodeRaw root = factory.open();
    //Then
    Assertions.assertThat(root.left.getBody()).isEqualTo(factory.left);
    Assertions.assertThat(root.left.getLines()).hasSize(5);
    Assertions.assertThat(root.right.getLines()).isEmpty();

    //When
    final NodeParsed parsed = factory.parse(root);
    final List<NodeParsed> sequence = parsed.mergeSequence();
    //Then
    Assertions.assertThat(sequence).hasSize(1);
    final NodeParsed seq1 = sequence.get(0);
    Assertions.assertThat(seq1.state).isEqualTo(NodeParsed.State.delete);
    Assertions.assertThat(seq1.left.lines).hasSize(5);
  }

  @Test
  void givenSource5andUpdate3ThenTarget3identicalAnd2delete() {
    //Given
    ScanDoc factory = ScanDoc.of(
      """
      A
      B
      C
      A
      B
      """,
      """
      A
      B
      C
      """
    );
    //When
    final NodeParsed parsed = factory.parse(factory.open());
    final List<NodeParsed> sequence = parsed.mergeSequence();
    //Then
    Assertions.assertThat(sequence).hasSize(2);
    Assertions.assertThat(sequence.get(0).state).isEqualTo(NodeParsed.State.identical);
    Assertions.assertThat(sequence.get(1).state).isEqualTo(NodeParsed.State.delete);
  }

  @Test
  void givenSource5andUpdate2ThenTarget2identicalAnd2deleteSegments() {
    //Given
    ScanDoc factory = ScanDoc.of(
      """
      A
      B
      C
      A
      B
      """,
      """
      B
      C
      """
    );
    //When
    final NodeRaw root = factory.open();
    //Then

    //When
    final NodeParsed parsed = factory.parse(root);
    final List<NodeParsed> sequence = parsed.mergeSequence();
    //Then
    Assertions.assertThat(sequence).hasSize(3);
    Assertions.assertThat(sequence.get(0).state).isEqualTo(NodeParsed.State.delete);
    Assertions.assertThat(sequence.get(1).state).isEqualTo(NodeParsed.State.identical);
    Assertions.assertThat(sequence.get(2).state).isEqualTo(NodeParsed.State.delete);
  }

  @Test
  void givenReplacementOfEwithC_thenIdentical2deleteEinsertC() {
    //Given
    ScanDoc factory = ScanDoc.of(
      """
      A
      B
      E
      """,
      """
      A
      B
      C
      """
    );
    //When
    final NodeRaw root = factory.open();
    //Then

    //When
    final NodeParsed parsed = factory.parse(root);
    final List<NodeParsed> sequence = parsed.mergeSequence();
    //Then

    Assertions.assertThat(sequence).hasSize(2);
    Assertions.assertThat(sequence.get(0).state).isEqualTo(NodeParsed.State.identical);
    Assertions.assertThat(sequence.get(1).state).isEqualTo(NodeParsed.State.update);
  }
}
