package tech.jhipster.lite.dsl.domain.clazz;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class DslClassCommentTest {

  @Test
  void shouldReturnComment() {
    DslClassComment classComment = new DslClassComment("/**  This is a comment   */");
    assertEquals("This is a comment", classComment.get());
  }

  @Test
  void shouldReturnComment2() {
    DslClassComment classComment = new DslClassComment("/*  This is a comment        */");
    assertEquals("This is a comment", classComment.get());
  }
}
