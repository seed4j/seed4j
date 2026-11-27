package com.seed4j.generator.server.springboot.banner;

import static java.util.Map.entry;

import com.seed4j.module.domain.properties.Seed4JProjectBaseName;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * ASCII art of a project base name, in the style of the JHipster banner: shadowed letters (FIGlet "ANSI Shadow" font) with a colored
 * first letter for each word, wrapped to fit in a 100 columns terminal
 */
record AsciiArt(String text) {
  private static final int MAX_LINE_WIDTH = 100;
  private static final int GLYPH_HEIGHT = 6;
  private static final String MARGIN = "  ";
  private static final String WORD_SPACING = "   ";
  private static final String FIRST_LETTER_COLOR = "${AnsiColor.GREEN}";
  private static final String OTHER_LETTERS_COLOR = "${AnsiColor.RED}";
  private static final Pattern GLYPH_ROW_SEPARATOR = Pattern.compile("\\|");
  private static final Pattern WORD_SEPARATOR = Pattern.compile("(?<=[a-z0-9])(?=[A-Z])");

  // prettier-ignore
  private static final Map<Character, String> GLYPHS = Map.ofEntries(
    entry('0', " ██████╗ |██╔═████╗|██║██╔██║|████╔╝██║|╚██████╔╝| ╚═════╝ "),
    entry('1', " ██╗|███║|╚██║| ██║| ██║| ╚═╝"),
    entry('2', "██████╗ |╚════██╗| █████╔╝|██╔═══╝ |███████╗|╚══════╝"),
    entry('3', "██████╗ |╚════██╗| █████╔╝| ╚═══██╗|██████╔╝|╚═════╝ "),
    entry('4', "██╗  ██╗|██║  ██║|███████║|╚════██║|     ██║|     ╚═╝"),
    entry('5', "███████╗|██╔════╝|███████╗|╚════██║|███████║|╚══════╝"),
    entry('6', " ██████╗ |██╔════╝ |███████╗ |██╔═══██╗|╚██████╔╝| ╚═════╝ "),
    entry('7', "███████╗|╚════██║|    ██╔╝|   ██╔╝ |   ██║  |   ╚═╝  "),
    entry('8', " █████╗ |██╔══██╗|╚█████╔╝|██╔══██╗|╚█████╔╝| ╚════╝ "),
    entry('9', " █████╗ |██╔══██╗|╚██████║| ╚═══██║| █████╔╝| ╚════╝ "),
    entry('A', " █████╗ |██╔══██╗|███████║|██╔══██║|██║  ██║|╚═╝  ╚═╝"),
    entry('B', "██████╗ |██╔══██╗|██████╔╝|██╔══██╗|██████╔╝|╚═════╝ "),
    entry('C', " ██████╗|██╔════╝|██║     |██║     |╚██████╗| ╚═════╝"),
    entry('D', "██████╗ |██╔══██╗|██║  ██║|██║  ██║|██████╔╝|╚═════╝ "),
    entry('E', "███████╗|██╔════╝|█████╗  |██╔══╝  |███████╗|╚══════╝"),
    entry('F', "███████╗|██╔════╝|█████╗  |██╔══╝  |██║     |╚═╝     "),
    entry('G', " ██████╗ |██╔════╝ |██║  ███╗|██║   ██║|╚██████╔╝| ╚═════╝ "),
    entry('H', "██╗  ██╗|██║  ██║|███████║|██╔══██║|██║  ██║|╚═╝  ╚═╝"),
    entry('I', "██╗|██║|██║|██║|██║|╚═╝"),
    entry('J', "     ██╗|     ██║|     ██║|██   ██║|╚█████╔╝| ╚════╝ "),
    entry('K', "██╗  ██╗|██║ ██╔╝|█████╔╝ |██╔═██╗ |██║  ██╗|╚═╝  ╚═╝"),
    entry('L', "██╗     |██║     |██║     |██║     |███████╗|╚══════╝"),
    entry('M', "███╗   ███╗|████╗ ████║|██╔████╔██║|██║╚██╔╝██║|██║ ╚═╝ ██║|╚═╝     ╚═╝"),
    entry('N', "███╗   ██╗|████╗  ██║|██╔██╗ ██║|██║╚██╗██║|██║ ╚████║|╚═╝  ╚═══╝"),
    entry('O', " ██████╗ |██╔═══██╗|██║   ██║|██║   ██║|╚██████╔╝| ╚═════╝ "),
    entry('P', "██████╗ |██╔══██╗|██████╔╝|██╔═══╝ |██║     |╚═╝     "),
    entry('Q', " ██████╗ |██╔═══██╗|██║   ██║|██║▄▄ ██║|╚██████╔╝| ╚══▀▀═╝ "),
    entry('R', "██████╗ |██╔══██╗|██████╔╝|██╔══██╗|██║  ██║|╚═╝  ╚═╝"),
    entry('S', "███████╗|██╔════╝|███████╗|╚════██║|███████║|╚══════╝"),
    entry('T', "████████╗|╚══██╔══╝|   ██║   |   ██║   |   ██║   |   ╚═╝   "),
    entry('U', "██╗   ██╗|██║   ██║|██║   ██║|██║   ██║|╚██████╔╝| ╚═════╝ "),
    entry('V', "██╗   ██╗|██║   ██║|██║   ██║|╚██╗ ██╔╝| ╚████╔╝ |  ╚═══╝  "),
    entry('W', "██╗    ██╗|██║    ██║|██║ █╗ ██║|██║███╗██║|╚███╔███╔╝| ╚══╝╚══╝ "),
    entry('X', "██╗  ██╗|╚██╗██╔╝| ╚███╔╝ | ██╔██╗ |██╔╝ ██╗|╚═╝  ╚═╝"),
    entry('Y', "██╗   ██╗|╚██╗ ██╔╝| ╚████╔╝ |  ╚██╔╝  |   ██║   |   ╚═╝   "),
    entry('Z', "███████╗|╚══███╔╝|  ███╔╝ | ███╔╝  |███████╗|╚══════╝")
  );

  AsciiArt {
    Assert.notBlank("text", text);
  }

  static AsciiArt from(Seed4JProjectBaseName baseName) {
    Assert.notNull("baseName", baseName);

    return new AsciiArt(lines(baseName).stream().map(AsciiArt::draw).collect(Collectors.joining("\n\n")));
  }

  private static List<List<String>> lines(Seed4JProjectBaseName baseName) {
    List<List<String>> lines = new ArrayList<>();

    words(baseName).forEach(word -> {
      if (!lines.isEmpty() && fits(lines.getLast(), word)) {
        lines.getLast().add(word);
      } else {
        lines.add(new ArrayList<>(List.of(word)));
      }
    });

    return lines;
  }

  private static Stream<String> words(Seed4JProjectBaseName baseName) {
    return WORD_SEPARATOR.splitAsStream(baseName.get())
      .map(word -> word.toUpperCase(Locale.ROOT))
      .flatMap(AsciiArt::wordParts);
  }

  private static Stream<String> wordParts(String word) {
    List<String> parts = new ArrayList<>();

    word
      .chars()
      .mapToObj(Character::toString)
      .forEach(letter -> {
        if (!parts.isEmpty() && width(List.of(parts.getLast() + letter)) <= MAX_LINE_WIDTH) {
          parts.add(parts.removeLast() + letter);
        } else {
          parts.add(letter);
        }
      });

    return parts.stream();
  }

  private static boolean fits(List<String> words, String word) {
    return width(Stream.concat(words.stream(), Stream.of(word)).toList()) <= MAX_LINE_WIDTH;
  }

  private static int width(List<String> words) {
    int lettersWidth = words
      .stream()
      .flatMapToInt(String::chars)
      .map(letter -> glyphRow(letter, 0).length())
      .sum();

    return MARGIN.length() + lettersWidth + (words.size() - 1) * WORD_SPACING.length();
  }

  private static String draw(List<String> words) {
    return IntStream.range(0, GLYPH_HEIGHT)
      .mapToObj(row -> drawRow(words, row))
      .collect(Collectors.joining("\n"));
  }

  private static String drawRow(List<String> words, int row) {
    return words
      .stream()
      .map(word -> drawWordRow(word, row))
      .collect(Collectors.joining(WORD_SPACING, MARGIN, ""))
      .stripTrailing();
  }

  private static String drawWordRow(String word, int row) {
    String otherLetters = word
      .chars()
      .skip(1)
      .mapToObj(letter -> glyphRow(letter, row))
      .collect(Collectors.joining());

    return FIRST_LETTER_COLOR + glyphRow(word.charAt(0), row) + OTHER_LETTERS_COLOR + otherLetters;
  }

  private static String glyphRow(int letter, int row) {
    return GLYPH_ROW_SEPARATOR.split(GLYPHS.get((char) letter))[row];
  }
}
