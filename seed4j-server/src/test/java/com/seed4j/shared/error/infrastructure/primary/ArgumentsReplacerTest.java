package com.seed4j.shared.error.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class ArgumentsReplacerTest {

  @Test
  void shouldNotReplaceArgumentsInNullMessage() {
    assertThat(ArgumentsReplacer.replaceParameters(null, Map.of("key", "value"))).isNull();
  }

  @Test
  void shouldNotReplaceUnknownArguments() {
    assertThat(ArgumentsReplacer.replaceParameters("Hey {{ user }}", null)).isEqualTo("Hey {{ user }}");
  }

  @Test
  void shouldReplaceKnownArguments() {
    assertThat(
      ArgumentsReplacer.replaceParameters("Hey {{ user }}, how's {{ friend }} doing? Say {{user}}", Map.of("user", "Joe"))
    ).isEqualTo("Hey Joe, how's {{ friend }} doing? Say Joe");
  }

  @Test
  void shouldReplaceObjectArguments() {
    Map<String, Object> arguments = new HashMap<>();
    arguments.put("number", 42);
    arguments.put("null", null);

    assertThat(ArgumentsReplacer.replaceParameters("Hey {{ number }}, how's {{ null }} doing?", arguments)).isEqualTo(
      "Hey 42, how's {{ null }} doing?"
    );
  }
}
