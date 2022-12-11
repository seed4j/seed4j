package tech.jhipster.lite.dsl.parser.domain;

public class ClassDslUtils {

  private ClassDslUtils() {}

  public static String cleanComment(String comment) {
    String lineSeparator = System.getProperty("line.separator", "\n");
    String[] lines = comment.split(lineSeparator);

    StringBuilder result = new StringBuilder();
    for (String line : lines) {
      line = line.trim();
      line = line.replace("/**", "").replace("/*", "").replace("*/", "").trim();
      if (line.startsWith("*")) {
        line = line.substring(1, line.length()).trim();
      }
      result.append(line).append(lineSeparator);
    }
    return result.toString().trim();
  }
}
