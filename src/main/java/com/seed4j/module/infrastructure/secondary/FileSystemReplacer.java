package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.Seed4JModule.LINE_BREAK;

import com.seed4j.module.domain.Seed4JModuleContext;
import com.seed4j.module.domain.file.TemplateRenderer;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.module.domain.replacement.ContentReplacer;
import com.seed4j.module.domain.replacement.ContentReplacers;
import com.seed4j.shared.error.domain.Assert;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;

@Service
public class FileSystemReplacer {

  private final TemplateRenderer templateRenderer;

  public FileSystemReplacer(TemplateRenderer templateRenderer) {
    this.templateRenderer = templateRenderer;
  }

  public void handle(Seed4JProjectFolder projectFolder, ContentReplacers replacers, Seed4JModuleContext context) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("replacers", replacers);

    replacers.forEach(applyReplacer(projectFolder, context));
  }

  private Consumer<ContentReplacer> applyReplacer(Seed4JProjectFolder projectFolder, Seed4JModuleContext context) {
    return replacement -> {
      Path filePath = projectFolder.filePath(replacement.file().get());

      try {
        String content = Files.readString(filePath);
        String updatedContent = replacement.apply(content);

        updatedContent = replacePlaceholders(updatedContent, context);

        Files.writeString(filePath, updatedContent.replace("\r\n", LINE_BREAK), StandardCharsets.UTF_8);
      } catch (IOException e) {
        replacement.handleError(e);
      }
    };
  }

  private String replacePlaceholders(String content, Seed4JModuleContext context) {
    return templateRenderer.render(content, context);
  }
}
