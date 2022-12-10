package tech.jhipster.lite.dsl.parser.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassDslUtils {

  private ClassDslUtils() {}

  public static String cleanComment(String comment) {
    String lineSeparator = System.getProperty("line.separator", "\n");
    String[] lines = comment.split(lineSeparator);

    Matcher matcher = Pattern.compile("^\\*?[ \t]*(.*)$").matcher("");

    boolean isBlockStarted = false;
    StringBuilder result = new StringBuilder();
    for (String line : lines) {
      line = line.trim();
      if (!isBlockStarted) {
        if (line.startsWith("/" + "*")) {
          if (line.endsWith("*" + "/")) {
            line = line.replace("/*", "").replace("*/", "");
            matcher.reset(line).matches();
            result.append(matcher.group(1).trim()).append(lineSeparator);
            continue;
          }
          //Assumes body starts on next line
          isBlockStarted = true;
        }
        continue;
      } else if (line.endsWith("*" + "/")) {
        if (line.startsWith("/" + "*")) {
          line = line.replace("/*", "").replace("*/", "");
          matcher.reset(line).matches();
          result.append(matcher.group(1).trim()).append(lineSeparator);
          continue;
        }
        isBlockStarted = false;
      } else {
        //Block is started
        matcher.reset(line).matches(); //Actually does the match

        //Trim to eliminate spaces between asterisk and text
        result.append(matcher.group(1).trim()).append(lineSeparator);
      }
    }
    return result.toString().trim();
  }

  public static String addQuote(String string) {
    return "\"" + string + "\"";
  }
}
