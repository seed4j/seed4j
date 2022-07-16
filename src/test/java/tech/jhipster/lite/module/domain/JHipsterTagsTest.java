package tech.jhipster.lite.module.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.lite.module.domain.JHipsterModuleTags.JHipsterModuleTagsBuilder;

import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.StringWithWitespacesException;

@UnitTest
class JHipsterTagsTest {

  @Test
  void shouldNotBeValidWithWhitespace() {
    JHipsterModuleTagsBuilder builder = JHipsterModuleTags.builder();
    assertThatThrownBy(() -> builder.add("my tag").build()).isInstanceOf(StringWithWitespacesException.class);
  }

  @Test
  void tagsShouldNotBeModifiable() {
    JHipsterModuleTags module = JHipsterModuleTags
      .builder()
      .add("spring")
      .add("database")
      .add(new JHipsterModuleTag("postgresql"))
      .add("foo", "bar", "baz")
      .add(List.of("tag1", "tag2"))
      .build();
    assertThatThrownBy(() -> module.get().add(new JHipsterModuleTag("unauthorized"))).isInstanceOf(UnsupportedOperationException.class);
  }
}
