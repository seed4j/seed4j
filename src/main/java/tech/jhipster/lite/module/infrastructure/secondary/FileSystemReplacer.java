package tech.jhipster.lite.module.infrastructure.secondary;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.module.domain.replacement.ContentReplacer;
import tech.jhipster.lite.module.domain.replacement.ContentReplacers;
import tech.jhipster.lite.shared.error.domain.Assert;

public class FileSystemReplacer {

  public void handle(JHipsterProjectFolder projectFolder, ContentReplacers replacers) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("replacers", replacers);

    replacers.forEach(applyReplacer(projectFolder));
  }

  private Consumer<ContentReplacer> applyReplacer(JHipsterProjectFolder projectFolder) {
    return replacement -> {
      Path filePath = projectFolder.filePath(replacement.file().get());

      try {
        String content = Files.readString(filePath);
        String updatedContent = replacement.apply(content);

        Files.writeString(filePath, updatedContent.replace("\r\n", LINE_BREAK), StandardCharsets.UTF_8);
      } catch (IOException e) {
        replacement.handleError(e);
      }
    };
  }
}
