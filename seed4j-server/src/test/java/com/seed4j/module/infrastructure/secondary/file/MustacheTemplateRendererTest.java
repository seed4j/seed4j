package com.seed4j.module.infrastructure.secondary.file;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class MustacheTemplateRendererTest {

  private final MustacheTemplateRenderer renderer = new MustacheTemplateRenderer();

  @Test
  void shouldNotReplaceUnknownArguments() {
    assertThat(renderer.render("Hey {{ user }}", Map.of())).isEqualTo("Hey {{ user }}");
  }

  @Test
  void shouldReplaceKnownArguments() {
    assertThat(renderer.render("Hey {{ user }}, how's {{ friend }} doing? Say {{user}}", Map.of("user", "Joe"))).isEqualTo(
      "Hey Joe, how's {{ friend }} doing? Say Joe"
    );
  }

  @Test
  void shouldReplaceObjectArguments() {
    Map<String, Object> arguments = new HashMap<>();
    arguments.put("number", 42);
    arguments.put("null", null);

    assertThat(renderer.render("Hey {{ number }}, how's {{ null }} doing?", arguments)).isEqualTo("Hey 42, how's {{ null }} doing?");
  }
}
