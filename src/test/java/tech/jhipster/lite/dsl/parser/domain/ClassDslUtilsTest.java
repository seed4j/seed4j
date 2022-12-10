package tech.jhipster.lite.dsl.parser.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class ClassDslUtilsTest {

  private final String lineSeparator = System.getProperty("line.separator", "\n");

  @Test
  void cleanComment() {
    String comment =
      """
                /**
                * first line of comment
                * second line of comment
                */
                """;
    String result = ClassDslUtils.cleanComment(comment);
    assertTrue(result.contains("first line of comment"));
    assertTrue(result.contains("second line of comment"));
    assertFalse(result.contains("*"));
  }

  @Test
  void cleanCommentRemoveLastLineIfEmpty() {
    String comment = """
                    /**
                     * from france
                     */
                """;
    String result = ClassDslUtils.cleanComment(comment);
    assertFalse(result.contains(lineSeparator));
  }

  @Test
  void cleanCommentRemoveLastLinesIfEmpty() {
    String comment =
      """
                    /**
                     * from france
                     *
                     *
                     
                     
                     */
                """;
    String result = ClassDslUtils.cleanComment(comment);
    assertFalse(result.contains(lineSeparator));
  }

  @Test
  void cleanCommentShouldNotRemoveValidEmptyLine() {
    String comment =
      """
                    /**
                     * test with valide line break after
                     *
                     * from france
                     *
                     *                     
                     
                     */
                """;
    String result = ClassDslUtils.cleanComment(comment);
    assertEquals(
      """
                        test with valide line break after
                            
                        from france""",
      result
    );
  }

  @Test
  void cleanCommentWithAsterixValid() {
    String comment =
      """
                /**
                * first line * of comment
                * second line of *comment
                */
                """;
    String result = ClassDslUtils.cleanComment(comment);
    assertTrue(result.contains("first line * of comment"));
    assertTrue(result.contains("second line of *comment"));
    assertTrue(result.contains("*"));
    assertTrue(result.contains(lineSeparator));
  }

  @Test
  void cleanCommentIfOneLine() {
    String comment = "/** comment for key */";
    String result = ClassDslUtils.cleanComment(comment);
    assertTrue(result.contains("comment for key"));
  }

  @Test
  void cleanCommentIfMultiLine() {
    String comment = """
            /** comment1 for key1 */
            /** comment2 for key2 */
            """;
    String result = ClassDslUtils.cleanComment(comment);
    assertFalse(result.contains("/*"));
    assertFalse(result.contains("*/"));
    assertTrue(result.contains("comment1 for key1"));
    assertTrue(result.contains("comment2 for key2"));
  }

  @Test
  void cleanCommentIfMultiLine2() {
    String comment = """
            /** comment1 for key2 */
            /**
             * comment2 for key2
             */
            """;
    String result = ClassDslUtils.cleanComment(comment);
    assertFalse(result.contains("/*"));
    assertFalse(result.contains("*/"));
    assertTrue(result.contains("comment1 for key2"));

    assertTrue(result.contains("comment2 for key2"));
  }

  @Test
  void cleanCommentIfMultiLine3() {
    String comment =
      """
            /**
            * comment1 for key2
            */
            /**
             * comment2 for key2
             */
            """;
    String result = ClassDslUtils.cleanComment(comment);
    assertFalse(result.contains("/*"));
    assertFalse(result.contains("*/"));
    assertTrue(result.contains("comment1 for key"));

    assertTrue(result.contains("comment2 for key2"));
  }
}
