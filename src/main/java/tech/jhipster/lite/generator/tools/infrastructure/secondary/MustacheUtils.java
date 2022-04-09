package tech.jhipster.lite.generator.tools.infrastructure.secondary;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import java.io.IOException;
import java.io.StringWriter;

public class MustacheUtils {

  public static final String EXT = ".mustache";

  private MustacheUtils() {}

  public static String template(String file, Object object) throws IOException {
    MustacheFactory mf = new DefaultMustacheFactory();
    Mustache m = mf.compile(file);

    StringWriter writer = new StringWriter();
    m.execute(writer, object).flush();

    return writer.toString();
  }

  public static String withExt(String value) {
    if (!value.endsWith(MustacheUtils.EXT)) {
      return value + MustacheUtils.EXT;
    }
    return value;
  }

  public static String withoutExt(String value) {
    if (value.endsWith(MustacheUtils.EXT)) {
      return value.replaceAll(EXT, "");
    }
    return value;
  }
}
