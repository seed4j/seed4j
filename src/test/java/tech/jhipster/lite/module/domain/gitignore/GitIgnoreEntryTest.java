package tech.jhipster.lite.module.domain.gitignore;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.gitignore.GitIgnoreEntry.GitIgnoreComment;

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
