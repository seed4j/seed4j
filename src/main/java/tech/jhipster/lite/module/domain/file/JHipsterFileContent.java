package tech.jhipster.lite.module.domain.file;

import java.nio.charset.StandardCharsets;
import tech.jhipster.lite.module.domain.JHipsterModuleContext;
import tech.jhipster.lite.module.domain.ProjectFiles;
import tech.jhipster.lite.shared.error.domain.Assert;

class JHipsterFileContent {

  private final JHipsterSource source;

  JHipsterFileContent(JHipsterSource source) {
    Assert.notNull("source", source);

    this.source = source;
  }

  public byte[] read(ProjectFiles files, JHipsterModuleContext context) {
    Assert.notNull("files", files);
    Assert.notNull("context", context);

    if (source.isNotTemplate()) {
      return files.readBytes(source.get().toString());
    }

    String rawContent = files.readString(source.get().toString());
    return ArgumentsReplacer.replaceArguments(rawContent, context.get()).getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public String toString() {
    return source.toString();
  }
}
