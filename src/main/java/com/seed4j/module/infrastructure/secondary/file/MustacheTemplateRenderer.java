package com.seed4j.module.infrastructure.secondary.file;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.DefaultMustacheVisitor;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheException;
import com.github.mustachejava.MustacheFactory;
import com.github.mustachejava.MustacheVisitor;
import com.github.mustachejava.TemplateContext;
import com.github.mustachejava.codes.ValueCode;
import com.seed4j.module.domain.file.TemplateRenderer;
import com.seed4j.shared.error.domain.GeneratorException;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public final class MustacheTemplateRenderer implements TemplateRenderer {

  private final MustacheFactory mustacheFactory = new CustomMustacheFactory();

  @Override
  @ExcludeFromGeneratedCodeCoverage(reason = "IOException is hard to test")
  public String render(String message, Map<String, ?> context) {
    Mustache mustache = mustacheFactory.compile(Reader.of(message), "template");
    try {
      StringWriter stringWriter = new StringWriter();
      mustache.execute(stringWriter, context).flush();
      return stringWriter.toString();
    } catch (IOException e) {
      throw GeneratorException.technicalError("Can't render template: " + e.getMessage(), e);
    }
  }

  private static final class CustomMustacheFactory extends DefaultMustacheFactory {

    @Override
    public MustacheVisitor createMustacheVisitor() {
      return new CustomMustacheVisitor(this);
    }
  }

  private static final class CustomMustacheVisitor extends DefaultMustacheVisitor {

    private CustomMustacheVisitor(DefaultMustacheFactory mustacheFactory) {
      super(mustacheFactory);
    }

    @Override
    public void value(TemplateContext templateContext, String variable, boolean encoded) {
      list.add(new CustomValueCode(templateContext, df, variable, encoded));
    }
  }

  /**
   * Custom implementation that keeps unknown variables rather than replacing them with an empty string.
   */
  private static final class CustomValueCode extends ValueCode {

    private CustomValueCode(
      TemplateContext templateContext,
      DefaultMustacheFactory defaultMustacheFactory,
      String variable,
      boolean encoded
    ) {
      super(templateContext, defaultMustacheFactory, variable, encoded);
    }

    @Override
    @ExcludeFromGeneratedCodeCoverage(reason = "Exception is hard to test")
    public Writer execute(Writer writer, List<Object> scopes) {
      try {
        final Object object = get(scopes);
        if (object == null) {
          writer.write("{{ " + name + " }}");
        }
        return super.execute(writer, scopes);
      } catch (Exception e) {
        throw new MustacheException("Failed to get value for " + name, e, tc);
      }
    }
  }
}
