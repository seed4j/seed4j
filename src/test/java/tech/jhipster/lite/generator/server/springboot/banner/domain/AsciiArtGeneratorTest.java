package tech.jhipster.lite.generator.server.springboot.banner.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AsciiArtGeneratorTest {

  @Test
  void shouldGenerateAsciiArt() {
    String ascii = AsciiArtGenerator.from("Jhipster");

    assertThat(ascii)
      .isEqualTo(
        """
      \s           █████  ██         ██                                                                                                                     \s
      \s           █████  ██         ██                    ███                                                                                              \s
      \s              ██  ██                               ███                                                                                              \s
      \s              ██  ██████   ████   ██████  ██████ ████████ ██████   ██████                                                                           \s
      \s              ██  ██████   ████   ███████ ██████ ████████ ███████  ██████                                                                           \s
      \s              ██  ███ ███    ██   ███ ███ ███      ███   ███  ███  ███                                                                              \s
      \s              ██  ██  ███    ██   ██  ███ ██████   ███   ████████  ███                                                                              \s
      \s              ██  ██  ███    ██   ██   ██  █████   ███   ████████  ██                                                                               \s
      \s         ██  ███  ██  ███    ██   ███ ███     ███  ███   ███       ██                                                                               \s
      \s         ███████  ██  ███ ███████ ███████ ███████  ██████ ███████  ██                                                                               \s
      \s         ███████  ██  ███ ███████ ██████  ██████    █████ ███████  ██                                                                               \s
      \s           ███                    █████    ███              ████                                                                                    \s
      \s                                  ██                                                                                                                \s
      \s                                  ██                                                                                                                \s"""
      );
  }

  @Test
  void shouldGenerateTruncatedAsciiArt() {
    String truncatedAscii = AsciiArtGenerator.from("Jhipster Sample App");

    assertThat(truncatedAscii).isEqualTo(AsciiArtGenerator.from("Jhipster Sample A"));
  }
}
