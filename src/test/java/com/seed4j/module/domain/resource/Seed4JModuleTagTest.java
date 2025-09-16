package com.seed4j.module.domain.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.MissingMandatoryValueException;
import com.seed4j.shared.error.domain.StringTooLongException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class Seed4JModuleTagTest {

  @Test
  void shouldNotBuildWithoutTag() {
    assertThatThrownBy(() -> new Seed4JModuleTag(null)).isInstanceOf(MissingMandatoryValueException.class);
  }

  @Test
  void shouldNotBuildWithWhitespace() {
    assertThatThrownBy(() -> new Seed4JModuleTag("my tag")).isInstanceOf(InvalidSeed4JModuleTagException.class);
  }

  @Test
  void shouldNotBuildWithTooLongTag() {
    var stringTooLong = RandomStringUtils.secure().nextNumeric(16);
    assertThatThrownBy(() -> new Seed4JModuleTag(stringTooLong)).isInstanceOf(StringTooLongException.class);
  }

  @ParameterizedTest
  @ValueSource(strings = { "MyTag", "my_tag", "myTag123" })
  void shouldNotBuildInvalidTag(String tag) {
    assertThatThrownBy(() -> new Seed4JModuleTag(tag)).isExactlyInstanceOf(InvalidSeed4JModuleTagException.class);
  }

  @Test
  void shouldGetTagValue() {
    assertThat(new Seed4JModuleTag("mytag").get()).isEqualTo("mytag");
  }
}
