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
 * ASCII art of a project base name, wrapped to fit in an 80 columns terminal
 */
record AsciiArt(String text) {
  private static final int MAX_LINE_LENGTH = 13;
  private static final int GLYPH_HEIGHT = 5;
  private static final String BLANK_GLYPH = "     |     |     |     |     ";
  private static final Pattern GLYPH_ROW_SEPARATOR = Pattern.compile("\\|");
  private static final Pattern WORD_SEPARATOR = Pattern.compile("(?<=[a-z0-9])(?=[A-Z])");
  private static final Pattern WORD_PART_SEPARATOR = Pattern.compile("(?<=\\G.{%d})".formatted(MAX_LINE_LENGTH));

  // prettier-ignore
  private static final Map<Character, String> GLYPHS = Map.ofEntries(
    entry('0', " ### |#  ##|# # #|##  #| ### "),
    entry('1', "  #  | ##  |  #  |  #  | ### "),
    entry('2', "#### |    #| ### |#    |#####"),
    entry('3', "#### |    #| ### |    #|#### "),
    entry('4', "#   #|#   #|#####|    #|    #"),
    entry('5', "#####|#    |#### |    #|#### "),
    entry('6', " ### |#    |#### |#   #| ### "),
    entry('7', "#####|   # |  #  | #   |#    "),
    entry('8', " ### |#   #| ### |#   #| ### "),
    entry('9', " ### |#   #| ####|    #| ### "),
    entry('A', " ### |#   #|#####|#   #|#   #"),
    entry('B', "#### |#   #|#### |#   #|#### "),
    entry('C', " ####|#    |#    |#    | ####"),
    entry('D', "#### |#   #|#   #|#   #|#### "),
    entry('E', "#####|#    |###  |#    |#####"),
    entry('F', "#####|#    |###  |#    |#    "),
    entry('G', " ####|#    |#  ##|#   #| ####"),
    entry('H', "#   #|#   #|#####|#   #|#   #"),
    entry('I', " ### |  #  |  #  |  #  | ### "),
    entry('J', "  ###|    #|    #|#   #| ### "),
    entry('K', "#   #|#  # |###  |#  # |#   #"),
    entry('L', "#    |#    |#    |#    |#####"),
    entry('M', "#   #|## ##|# # #|#   #|#   #"),
    entry('N', "#   #|##  #|# # #|#  ##|#   #"),
    entry('O', " ### |#   #|#   #|#   #| ### "),
    entry('P', "#### |#   #|#### |#    |#    "),
    entry('Q', " ### |#   #|# # #|#  # | ## #"),
    entry('R', "#### |#   #|#### |#  # |#   #"),
    entry('S', " ####|#    | ### |    #|#### "),
    entry('T', "#####|  #  |  #  |  #  |  #  "),
    entry('U', "#   #|#   #|#   #|#   #| ### "),
    entry('V', "#   #|#   #|#   #| # # |  #  "),
    entry('W', "#   #|#   #|# # #|## ##|#   #"),
    entry('X', "#   #| # # |  #  | # # |#   #"),
    entry('Y', "#   #| # # |  #  |  #  |  #  "),
    entry('Z', "#####|   # |  #  | #   |#####")
  );

  AsciiArt {
    Assert.notBlank("text", text);
  }

  static AsciiArt from(Seed4JProjectBaseName baseName) {
    Assert.notNull("baseName", baseName);

    return new AsciiArt(textLines(baseName).stream().map(AsciiArt::draw).collect(Collectors.joining("\n\n")));
  }

  private static List<String> textLines(Seed4JProjectBaseName baseName) {
    List<String> lines = new ArrayList<>();

    words(baseName).forEach(word -> {
      if (!lines.isEmpty() && fits(lines.getLast(), word)) {
        lines.add(lines.removeLast() + " " + word);
      } else {
        lines.add(word);
      }
    });

    return lines;
  }

  private static Stream<String> words(Seed4JProjectBaseName baseName) {
    return WORD_SEPARATOR.splitAsStream(baseName.get())
      .flatMap(WORD_PART_SEPARATOR::splitAsStream)
      .map(word -> word.toUpperCase(Locale.ROOT));
  }

  private static boolean fits(String line, String word) {
    return line.length() + 1 + word.length() <= MAX_LINE_LENGTH;
  }

  private static String draw(String line) {
    List<String[]> glyphs = line.chars().mapToObj(AsciiArt::glyphRows).toList();

    return IntStream.range(0, GLYPH_HEIGHT)
      .mapToObj(row ->
        glyphs
          .stream()
          .map(glyph -> glyph[row])
          .collect(Collectors.joining(" "))
          .stripTrailing()
      )
      .collect(Collectors.joining("\n"));
  }

  private static String[] glyphRows(int character) {
    return GLYPH_ROW_SEPARATOR.split(GLYPHS.getOrDefault((char) character, BLANK_GLYPH));
  }
}
