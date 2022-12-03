package tech.jhipster.lite.dsl.common.domain.clazz;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassComment;

@UnitTest
class ClassCommentTest {

  @Test
  void shouldReturnComment() {
    ClassComment classComment = new ClassComment("/**  This is a comment   */");
    assertEquals("This is a comment", classComment.get());
  }

  @Test
  void shouldReturnComment2() {
    ClassComment classComment = new ClassComment("/*  This is a comment        */");
    assertEquals("This is a comment", classComment.get());
  }
}
