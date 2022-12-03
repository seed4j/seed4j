package tech.jhipster.lite.dsl.parser.domain;

public class ClassDslUtils {

  private ClassDslUtils() {}

  public static String cleanComment(String comment) {
    return comment.replace("/**", "").replace("/*", "").replace("*/", "").trim();
  }

  public static String addQuote(String string) {
    return "\"" + string + "\"";
  }
}
