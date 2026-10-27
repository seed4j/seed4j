package com.seed4j.module.domain.gitignore;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.gitignore.GitIgnoreEntry.GitIgnoreComment;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class GitIgnoreEntryTest {

  @Nested
  class GitIgnoreCommentTest {

    @Test
    void shouldAutomaticallyAddCommentPrefixIfMissing() {
      GitIgnoreComment comment = new GitIgnoreComment("A comment");

      assertThat(comment.get()).isEqualTo("# A comment");
    }

    @Test
    void shouldNotAutomaticallyAddCommentPrefixIfAlreadyPresent() {
      GitIgnoreComment comment = new GitIgnoreComment("#A comment");

      assertThat(comment.get()).isEqualTo("#A comment");
    }
  }
}
